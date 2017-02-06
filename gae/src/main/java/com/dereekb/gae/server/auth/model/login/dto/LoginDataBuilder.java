package com.dereekb.gae.server.auth.model.login.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.search.document.dto.DescribedModelDataBuilder;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link Login} to
 * {@link LoginData}.
 *
 * @author dereekb
 */
public final class LoginDataBuilder extends DescribedModelDataBuilder<Login, LoginData> {

	public LoginDataBuilder() {
		super(LoginData.class);
	}

	public LoginDataBuilder(Factory<LoginData> factory) throws IllegalArgumentException {
		super(factory);
	}

	@Override
	public LoginData convertSingle(Login input) throws ConversionFailureException {
		LoginData data = super.convertSingle(input);

		// Data
		data.setDate(input.getDate());
		data.setRoles(input.getRoles());
		data.setGroup(input.getGroup());

		// Links
		data.setPointers(ObjectifyKeyUtility.readKeyNames(input.getPointers()));

		return data;
	}

}
