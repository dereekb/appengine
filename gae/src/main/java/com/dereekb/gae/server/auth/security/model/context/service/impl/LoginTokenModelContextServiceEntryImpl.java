package com.dereekb.gae.server.auth.security.model.context.service.impl;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextRoleSetEncoderDecoder;
import com.dereekb.gae.server.auth.security.model.context.encoded.impl.LoginTokenModelContextSetEncoderDecoderEntryImpl;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceEntry;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;

/**
 * {@link LoginTokenModelContextServiceEntry} implementation that extends
 * {@link LoginTokenModelContextSetEncoderDecoderEntryImpl}.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextServiceEntryImpl<T extends UniqueModel> extends LoginTokenModelContextSetEncoderDecoderEntryImpl
        implements LoginTokenModelContextServiceEntry {

	private Getter<T> getter;

	public LoginTokenModelContextServiceEntryImpl(Integer code,
	        String modelType,
	        ModelKeyType keyType,
	        LoginTokenModelContextRoleSetEncoderDecoder rolesDencoder) {
		super(code, modelType, keyType, rolesDencoder);
	}

	public LoginTokenModelContextServiceEntryImpl(Integer code,
	        String modelType,
	        StringModelKeyConverter keyConverter,
	        LoginTokenModelContextRoleSetEncoderDecoder rolesDencoder) {
		super(code, modelType, keyConverter, rolesDencoder);
	}

	// MARK: LoginTokenModelContextServiceEntry
	@Override
	public LoginTokenTypedModelContextSet makeTypedContextSet(Set<String> keys,
	                                                          boolean atomic)
	        throws AtomicOperationException {

		List<ModelKey> modelKeys = this.getKeyConverter().convert(keys);

		// TODO: Use a getter or a readService here? The readService wouldn't be
		// allowed to load specific objects that we might ultimately be granting
		// access to. For instance, if we're invited to a party, but our
		// permissions currently do not allow us to read the party, which is why
		// we're getting the context.

		// TODO: Add atomic reading using getter.
		
		List<T> models = this.getter.getWithKeys(modelKeys);
		

		return keys;
	}

}
