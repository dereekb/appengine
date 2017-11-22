package com.dereekb.gae.server.auth.security.model.context.encoded.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoderDecoderEntry;
import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.encoded.ModelRoleSetEncoderDecoder;
import com.dereekb.gae.server.auth.security.model.roles.impl.AbstractModelRoleSet;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapAndSet;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link LoginTokenModelContextSetEncoderDecoderEntry} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenModelContextSetEncoderDecoderEntryImpl
        implements LoginTokenModelContextSetEncoderDecoderEntry {

	public static final String ROLE_SPLITTER = ":::";
	public static final String KEYS_ROLE_JOINER = ":";
	public static final String KEY_SPLITTER = StringUtility.DEFAULT_SEPARATOR;

	private String modelType;
	private StringModelKeyConverter keyConverter;
	private ModelRoleSetEncoderDecoder rolesDencoder;

	public LoginTokenModelContextSetEncoderDecoderEntryImpl(String modelType,
	        ModelKeyType keyType,
	        ModelRoleSetEncoderDecoder rolesDencoder) {
		this(modelType, ModelKey.converterForKeyType(keyType), rolesDencoder);
	}

	public LoginTokenModelContextSetEncoderDecoderEntryImpl(String modelType,
	        StringModelKeyConverter keyConverter,
	        ModelRoleSetEncoderDecoder rolesDencoder) {
		super();
		this.setModelType(modelType);
		this.setKeyConverter(keyConverter);
		this.setRolesDencoder(rolesDencoder);
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

	public ModelRoleSetEncoderDecoder getRolesDencoder() {
		return this.rolesDencoder;
	}

	public void setRolesDencoder(ModelRoleSetEncoderDecoder rolesDencoder) {
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
			ModelRoleSet roleSet = context.getRoleSet();
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
	 * Lazy-loaded {@link ModelRoleSet} implementation.
	 *
	 * @author dereekb
	 *
	 */
	protected class LazyLoginTokenModelRoleContext extends AbstractModelRoleSet {

		private final String encodedRoles;
		private transient Set<ModelRole> contextRoles;

		public LazyLoginTokenModelRoleContext(String encodedRoles) {
			super();
			this.encodedRoles = encodedRoles;
		}

		// MARK: ModelRoleSet
		@Override
		public boolean isEmpty() {

			// TODO: This might not be true in some cases. A value of "0" is
			// empty, but this would return true, for instance. It really banks
			// on the fact that empty encodings are ignored. In this case, this
			// function should always return true really.

			return StringUtility.isEmptyString(this.encodedRoles);
		}

		@Override
		public Set<ModelRole> getRoles() {
			if (this.contextRoles == null) {
				this.contextRoles = this.decodeRoles();
			}

			return this.contextRoles;
		}

		protected Set<ModelRole> decodeRoles() {
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
			public ModelRoleSet getRoleSet() {
				return LazyLoginTokenModelRoleContext.this;
			}

		}

	}

	@Override
	public String toString() {
		return "LoginTokenModelContextSetEncoderDecoderEntryImpl [modelType=" + this.modelType + ", keyConverter="
		        + this.keyConverter + ", rolesDencoder=" + this.rolesDencoder + "]";
	}

}
