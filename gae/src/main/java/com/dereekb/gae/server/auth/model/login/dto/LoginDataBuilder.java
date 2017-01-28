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

	// Single Directional Converter
	@Override
	public LoginData convertSingle(Login input) throws ConversionFailureException {
		LoginData data = new LoginData();

		// Id
		data.setModelKey(input.getModelKey());
		data.setDate(input.getDate());
		data.setSearchIdentifier(input.getSearchIdentifier());

		// Links
		data.setPointers(ObjectifyKeyUtility.readKeyNames(input.getPointers()));

		// Data
		data.setRoles(input.getRoles());
		data.setGroup(input.getGroup());

		// Descriptor
		data.setDescriptor(input.getDescriptor());

		return data;
	}

}
