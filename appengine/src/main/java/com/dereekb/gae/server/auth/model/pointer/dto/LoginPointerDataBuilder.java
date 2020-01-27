package com.dereekb.gae.server.auth.model.pointer.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelDataBuilder;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link LoginPointer} to
 * {@link LoginPointerData}.
 *
 * @author dereekb
 */
public final class LoginPointerDataBuilder extends OwnedDatabaseModelDataBuilder<LoginPointer, LoginPointerData> {

	public LoginPointerDataBuilder() {
		super(LoginPointerData.class);
	}

	// Single Directional Converter
	@Override
	public LoginPointerData convertSingle(LoginPointer input) throws ConversionFailureException {
		LoginPointerData data = super.convertSingle(input);

		// Data
		data.setEmail(input.getEmail());
		data.setType(input.getTypeId());
		data.setDisabled(input.getDisabled());

		// Links
		data.setLogin(ObjectifyKeyUtility.readKeyIdentifier(input.getLogin()));

		return data;
	}

}
