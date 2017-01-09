package com.dereekb.gae.server.auth.model.key.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link Login} to
 * {@link LoginData}.
 *
 * @author dereekb
 */
public final class LoginKeyDataBuilder extends AbstractDirectionalConverter<LoginKey, LoginKeyData> {

	public LoginKeyDataBuilder() {}

	// Single Directional Converter
	@Override
	public LoginKeyData convertSingle(LoginKey loginKey) throws ConversionFailureException {
		LoginKeyData data = new LoginKeyData();

		// Id
		data.setIdentifier(loginKey.getModelKey());
		data.setCreated(loginKey.getCreated());

		// Pointers
		data.setPointer(ObjectifyKeyUtility.nameFromKey(loginKey.getLoginPointer()));

		// Data
		data.setName(loginKey.getName());
		data.setMask(loginKey.getMask());
		data.setVerification(loginKey.getVerification());
		data.setExpiration(loginKey.getExpiration());
		
		return data;
	}

}
