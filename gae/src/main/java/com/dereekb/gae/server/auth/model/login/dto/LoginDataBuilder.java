package com.dereekb.gae.server.auth.model.login.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link Login} to
 * {@link LoginData}.
 *
 * @author dereekb
 */
public final class LoginDataBuilder extends AbstractDirectionalConverter<Login, LoginData> {

	public LoginDataBuilder() {}

	// Single Directional Converter
	@Override
	public LoginData convertSingle(Login login) throws ConversionFailureException {
		LoginData data = new LoginData();

		// Id
		data.setIdentifier(login.getModelKey());
		data.setCreated(login.getDate());
		data.setSearchIdentifier(login.getSearchIdentifier());

		// Links
		data.setPointers(ObjectifyKeyUtility.readKeyNames(login.getPointers()));

		// Data
		data.setRoles(login.getRoles());
		data.setType(login.getType());

		return data;
	}

}
