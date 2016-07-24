package com.dereekb.gae.server.auth.model.pointer.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link LoginPointer} to
 * {@link LoginPointerData}.
 *
 * @author dereekb
 */
public final class LoginPointerDataBuilder extends AbstractDirectionalConverter<LoginPointer, LoginPointerData> {

	public LoginPointerDataBuilder() {}

	// Single Directional Converter
	@Override
	public LoginPointerData convertSingle(LoginPointer loginPointer) throws ConversionFailureException {
		LoginPointerData data = new LoginPointerData();

		// Id
		data.setIdentifier(loginPointer.getModelKey());

		// Data
		data.setEmail(loginPointer.getEmail());

		// Links
		data.setLogin(ObjectifyKeyUtility.readKeyIdentifier(loginPointer.getLogin()));

		return data;
	}

}
