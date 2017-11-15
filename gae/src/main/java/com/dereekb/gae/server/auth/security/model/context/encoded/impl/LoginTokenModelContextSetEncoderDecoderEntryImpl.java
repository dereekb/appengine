package com.dereekb.gae.server.auth.security.model.context.encoded.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRole;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRoleSet;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextRoleSetEncoderDecoder;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoderDecoderEntry;
import com.dereekb.gae.server.auth.security.model.context.impl.AbstractLoginTokenModelContextRoleSet;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapAndSet;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * Abstract {@link LoginTokenModelContextSetEncoderDecoderEntry}
 * implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextSetEncoderDecoderEntryImpl
        implements LoginTokenModelContextSetEncoderDecoderEntry {

	public static final String ROLE_SPLITTER = ":::";
	public static final String KEYS_ROLE_JOINER = ":";
	public static final String KEY_SPLITTER = StringUtility.DEFAULT_SEPARATOR;

	private Integer code;
	private String modelType;
	private StringModelKeyConverter keyConverter;
	private LoginTokenModelContextRoleSetEncoderDecoder rolesDencoder;

	public LoginTokenModelContextSetEncoderDecoderEntryImpl(Integer code,
	        String modelType,
	        ModelKeyType keyType,
	        LoginTokenModelContextRoleSetEncoderDecoder rolesDencoder) {
		this(code, modelType, ModelKey.converterForKeyType(keyType), rolesDencoder);
	}

	public LoginTokenModelContextSetEncoderDecoderEntryImpl(Integer code,
	        String modelType,
	        StringModelKeyConverter keyConverter,
	        LoginTokenModelContextRoleSetEncoderDecoder rolesDencoder) {
		super();
		this.setCode(code);
		this.setModelType(modelType);
		this.setKeyConverter(keyConverter);
		this.setRolesDencoder(rolesDencoder);
	}

	@Override
	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		if (code == null) {
			throw new IllegalArgumentException("code cannot be null.");
		}

		this.code = code;
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		if (modelType == null) {
			throw new IllegalArgumentException("modelType cannot be null.");
		}

		this.modelType = modelType;
	}

	public StringModelKeyConverter getKeyConverter() {
		return this.keyConverter;
	}

	public void setKeyConverter(StringModelKeyConverter keyConverter) {
		if (keyConverter == null) {
			throw new IllegalArgumentException("keyConverter cannot be null.");
		}

		this.keyConverter = keyConverter;
	}

	public LoginTokenModelContextRoleSetEncoderDecoder getRolesDencoder() {
		return this.rolesDencoder;
	}

	public void setRolesDencoder(LoginTokenModelContextRoleSetEncoderDecoder rolesDencoder) {
		if (rolesDencoder == null) {
			throw new IllegalArgumentException("rolesDencoder cannot be null.");
		}

		this.rolesDencoder = rolesDencoder;
	}

	// MARK: LoginTokenModelContextSetEncoderDecoderDelegate
	@Override
	public String encode(LoginTokenTypedModelContextSet typedSet) {
		CaseInsensitiveMapAndSet set = this.makeEncodedRoleAndKeySet(typedSet);
		List<String> rolePartitions = new ArrayList<String>(set.keySet().size());

		for (Entry<String, Set<String>> roleSet : set.entrySet()) {
			String role = roleSet.getKey();
			Set<String> modelKeys = roleSet.getValue();

			String keysString = StringUtility.joinValues(KEY_SPLITTER, modelKeys);
			String rolePartition = keysString + KEYS_ROLE_JOINER + role;	// Role
			                                                            	// Last
			rolePartitions.add(rolePartition);
		}

		return StringUtility.joinValues(ROLE_SPLITTER, rolePartitions);
	}

	protected CaseInsensitiveMapAndSet makeEncodedRoleAndKeySet(LoginTokenTypedModelContextSet typedSet) {
		List<LoginTokenModelContext> contexts = typedSet.getContexts();
		CaseInsensitiveMapAndSet set = new CaseInsensitiveMapAndSet();

		for (LoginTokenModelContext context : contexts) {
			LoginTokenModelContextRoleSet roleSet = context.getRoleSet();
			String encodedRoles = this.rolesDencoder.encodeRoleSet(roleSet);

			ModelKey key = context.getModelKey();
			set.add(encodedRoles, key.toString());
		}

		return set;
	}

	@Override
	public List<LoginTokenModelContext> decode(String encodedContext) {

		List<LoginTokenModelContext> contexts = new ArrayList<LoginTokenModelContext>();
		List<String> rolePartitions = StringUtility.separateValues(ROLE_SPLITTER, encodedContext);

		for (String rolePartition : rolePartitions) {
			String[] roleAndKeys = rolePartition.split(KEYS_ROLE_JOINER);

			String keysString = roleAndKeys[0];
			String encodedRoles = roleAndKeys[1];

			LazyLoginTokenModelRoleContext roleContext = new LazyLoginTokenModelRoleContext(encodedRoles);
			contexts.addAll(roleContext.makeContexts(keysString));
		}

		return contexts;
	}

	/**
	 * Lazy-loaded {@link LoginTokenModelContextRoleSet} implementation.
	 * 
	 * @author dereekb
	 *
	 */
	protected class LazyLoginTokenModelRoleContext extends AbstractLoginTokenModelContextRoleSet {

		private final String encodedRoles;
		private transient Set<LoginTokenModelContextRole> contextRoles;

		public LazyLoginTokenModelRoleContext(String encodedRoles) {
			super();
			this.encodedRoles = encodedRoles;
		}

		// MARK: LoginTokenModelContextRoleSet
		@Override
		public Set<LoginTokenModelContextRole> getRoles() {
			if (this.contextRoles == null) {
				this.contextRoles = this.decodeRoles();
			}

			return this.contextRoles;
		}

		protected Set<LoginTokenModelContextRole> decodeRoles() {
			return LoginTokenModelContextSetEncoderDecoderEntryImpl.this.rolesDencoder.decodeRoleSet(this.encodedRoles);
		}

		// MARK: Contexts
		public List<LoginTokenModelContext> makeContexts(String keysString) {
			List<String> stringKeys = StringUtility.separateValues(KEY_SPLITTER, keysString);
			List<ModelKey> keys = LoginTokenModelContextSetEncoderDecoderEntryImpl.this.keyConverter
			        .convertTo(stringKeys);

			List<LoginTokenModelContext> contexts = new ArrayList<LoginTokenModelContext>();

			for (ModelKey key : keys) {
				contexts.add(new LazyLoginTokenModelContext(key));
			}

			return contexts;
		}

		protected class LazyLoginTokenModelContext
		        implements LoginTokenModelContext {

			private final ModelKey modelKey;

			public LazyLoginTokenModelContext(ModelKey modelKey) {
				super();
				this.modelKey = modelKey;
			}

			// MARK: LoginTokenModelContext
			@Override
			public String getModelType() {
				return LoginTokenModelContextSetEncoderDecoderEntryImpl.this.modelType;
			}

			@Override
			public ModelKey getModelKey() {
				return this.modelKey;
			}

			@Override
			public ModelKey keyValue() {
				return this.getModelKey();
			}

			@Override
			public LoginTokenModelContextRoleSet getRoleSet() {
				return LazyLoginTokenModelRoleContext.this;
			}

		}

	}

	@Override
	public String toString() {
		return "LoginTokenModelContextSetEncoderDecoderEntryImpl [code=" + this.code + ", modelType=" + this.modelType
		        + ", keyConverter=" + this.keyConverter + ", rolesDencoder=" + this.rolesDencoder + "]";
	}

}
