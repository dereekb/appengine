package com.dereekb.gae.server.auth.security.model.context.encoded.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoderDecoder;
import com.dereekb.gae.server.auth.security.model.context.exception.UnavailableModelContextTypeException;
import com.dereekb.gae.utilities.collections.map.CrossKeyMap;
import com.dereekb.gae.utilities.collections.map.impl.CrossKeyMapImpl;

/**
 * {@link LoginTokenModelContextSetEncoderDecoder} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextSetEncoderDecoderImpl
        implements LoginTokenModelContextSetEncoderDecoder {

	private CrossKeyMap<Integer, String> typeMap;
	private Map<String, LoginTokenModelContextSetEncoderDecoderDelegate> delegates;

	public LoginTokenModelContextSetEncoderDecoderImpl() {
		this(new ArrayList<LoginTokenModelContextSetEncoderDecoderDelegate>());
	}

	public LoginTokenModelContextSetEncoderDecoderImpl(
	        List<LoginTokenModelContextSetEncoderDecoderDelegate> delegates) {
		this.setDelegates(delegates);
	}

	public void setDelegates(List<LoginTokenModelContextSetEncoderDecoderDelegate> delegates) {
		this.typeMap = new CrossKeyMapImpl<Integer, String>();
		this.delegates = new HashMap<String, LoginTokenModelContextSetEncoderDecoderDelegate>();

		for (LoginTokenModelContextSetEncoderDecoderDelegate delegate : delegates) {
			Integer code = delegate.getCode();
			String type = delegate.getModelType();

			this.typeMap.put(code, type);
			this.delegates.put(type, delegate);
		}
	}

	// MARK: Encode
	@Override
	public EncodedLoginTokenModelContextSet encodeSet(LoginTokenModelContextSet set) {
		Map<Integer, String> map = new HashMap<Integer, String>();

		Set<String> types = set.getModelTypes();
		
		for (String type : types) {
			LoginTokenModelContextSetEncoderDecoderDelegate delegate = LoginTokenModelContextSetEncoderDecoderImpl.this
			        .getDelegate(type);
			
			LoginTokenTypedModelContextSet typedSet = set.getContextsForType(type);
			Integer key = this.typeMap.getX(type);
			String encoded = delegate.encode(typedSet);
			map.put(key, encoded);
		}
		
		return new EncodedLoginTokenModelContextSetImpl(map); 
	}

	// MARK: Decode
	@Override
	public LoginTokenModelContextSet decodeSet(EncodedLoginTokenModelContextSet set) {
		return new DecodedLoginTokenModelContextSet(set);
	}

	/**
	 * Lazy-decoded {@link LoginTokenModelContextSet} implementation.
	 * 
	 * @author dereekb
	 *
	 */
	protected class DecodedLoginTokenModelContextSet
	        implements LoginTokenModelContextSet {

		private final Set<String> typeSet;
		private final EncodedLoginTokenModelContextSet encodedSet;

		private transient Set<String> uninitialized;
		private transient Map<String, LoginTokenTypedModelContextSet> setMap = new HashMap<String, LoginTokenTypedModelContextSet>();

		public DecodedLoginTokenModelContextSet(EncodedLoginTokenModelContextSet encodedSet) {
			this.typeSet = this.initWithSet(encodedSet);
			this.uninitialized = new HashSet<String>(this.typeSet);
			this.encodedSet = encodedSet;
		}

		protected Set<String> initWithSet(EncodedLoginTokenModelContextSet set) {
			Set<Integer> typeCodes = set.getEncodedModelContextTypes();
			Set<String> typeSet = new HashSet<String>();

			for (Integer code : typeCodes) {
				String modelType = LoginTokenModelContextSetEncoderDecoderImpl.this.getTypeForCode(code);
				typeSet.add(modelType);
			}

			return typeSet;
		}

		// MARK: LoginTokenModelContextSet
		@Override
		public Map<String, LoginTokenTypedModelContextSet> getSetMap() {
			this.initEntireMap();
			return this.setMap;
		}

		@Override
		public Set<String> getModelTypes() {
			return this.typeSet;
		}

		@Override
		public boolean hasContextForType(String modelType) {
			return this.typeSet.contains(modelType);
		}

		@Override
		public LoginTokenTypedModelContextSet getContextsForType(String modelType)
		        throws UnavailableModelContextTypeException {
			this.initType(modelType);
			return this.setMap.get(modelType);
		}

		// MARK: Internal
		private void initEntireMap() {
			if (this.uninitialized.isEmpty() == false) {
				Set<String> remaining = new HashSet<String>(this.uninitialized);
				for (String type : remaining) {
					this.initType(type);
				}
			}
		}

		private void initType(String modelType) {
			if (!this.uninitialized.contains(modelType)) {
				LoginTokenTypedModelContextSet set = new LoginTokenTypedModelContextSetImpl(modelType);
				this.setMap.put(modelType, set);
				
				// Remove from uninitialized.
				this.uninitialized.remove(modelType);
			}
		}

		// MARK: Context Set
		protected class LoginTokenTypedModelContextSetImpl
		        implements LoginTokenTypedModelContextSet {

			private final String modelType;
			private transient List<LoginTokenModelContext> contexts;

			public LoginTokenTypedModelContextSetImpl(String modelType) {
				this.modelType = modelType;
			}

			// MARK:
			@Override
			public String getModelType() {
				return this.modelType;
			}

			@Override
			public List<LoginTokenModelContext> getContexts() {
				if (this.contexts == null) {
					this.contexts = this.decodeContexts();
				}

				return this.contexts;
			}

			protected List<LoginTokenModelContext> decodeContexts() {
				Integer encodedType = LoginTokenModelContextSetEncoderDecoderImpl.this.typeMap.getX(this.modelType);
				String encodedContext = DecodedLoginTokenModelContextSet.this.encodedSet
				        .getEncodedModelTypeContext(encodedType);
				LoginTokenModelContextSetEncoderDecoderDelegate delegate = LoginTokenModelContextSetEncoderDecoderImpl.this
				        .getDelegate(this.modelType);
				return delegate.decode(encodedContext);
			}

		}

	}

	// MARK: Internal
	protected LoginTokenModelContextSetEncoderDecoderDelegate getDelegate(Integer code) {
		String type = this.getTypeForCode(code);
		return this.getDelegate(type);
	}

	protected String getTypeForCode(Integer code) {
		String type = this.typeMap.getY(code);

		if (type == null) {
			throw new UnavailableModelContextTypeException();
		}

		return type;
	}

	protected Integer getCodeForType(String type) {
		Integer code = this.typeMap.getX(type);

		if (code == null) {
			throw new UnavailableModelContextTypeException();
		}

		return code;
	}

	protected LoginTokenModelContextSetEncoderDecoderDelegate getDelegate(String modelType) {
		LoginTokenModelContextSetEncoderDecoderDelegate delegate = this.delegates.get(modelType);

		if (delegate == null) {
			throw new UnavailableModelContextTypeException(modelType);
		}

		return delegate;
	}

}
