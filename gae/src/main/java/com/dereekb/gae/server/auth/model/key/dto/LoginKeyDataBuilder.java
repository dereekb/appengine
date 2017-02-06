package com.dereekb.gae.server.auth.model.key.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelDataBuilder;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link LoginKey} to
 * {@link LoginKeyData}.
 *
 * @author dereekb
 */
public final class LoginKeyDataBuilder extends OwnedDatabaseModelDataBuilder<LoginKey, LoginKeyData> {

	public LoginKeyDataBuilder() {
		super(LoginKeyData.class);
	}

	public LoginKeyDataBuilder(Factory<LoginKeyData> factory) {
		super(factory);
	}

	// Single Directional Converter
	@Override
	public LoginKeyData convertSingle(LoginKey input) throws ConversionFailureException {
		LoginKeyData data = super.convertSingle(input);

		// Data
		data.setDate(input.getDate());
		data.setName(input.getName());
		data.setMask(input.getMask());
		data.setVerification(input.getVerification());
		data.setExpiration(input.getExpiration());

		// Links
		data.setPointer(ObjectifyKeyUtility.nameFromKey(input.getLoginPointer()));

		return data;
	}

}
