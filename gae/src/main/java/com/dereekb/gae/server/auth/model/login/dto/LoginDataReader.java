package com.dereekb.gae.server.auth.model.login.dto;

import java.util.Set;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.utilities.misc.reader.DateLongConverter;
import com.googlecode.objectify.Key;

/**
 * {@link DirectionalConverter} for converting a {@link Login} to
 * {@link LoginData}.
 *
 * @author dereekb
 */
public final class LoginDataReader extends AbstractDirectionalConverter<LoginData, Login> {

	private static final StringModelKeyConverter KEY_CONVERTER = StringLongModelKeyConverterImpl.CONVERTER;

	private static final ObjectifyKeyUtility<LoginPointer> LOGIN_POINTER_KEY_UTIL = ObjectifyKeyUtility
	        .make(LoginPointer.class);

	@Override
	public Login convertSingle(LoginData input) throws ConversionFailureException {
		Login model = new Login();

		// Identifier
		String stringIdentifier = input.getKey();
		model.setModelKey(KEY_CONVERTER.safeConvert(stringIdentifier));

		Long date = input.getCreated();
		model.setDate(DateLongConverter.CONVERTER.safeConvert(date));

		model.setSearchIdentifier(input.getSearchIdentifier());

		// Pointers
		Set<Key<LoginPointer>> pointers = LOGIN_POINTER_KEY_UTIL.setFromStrings(input.getPointers());
		model.setPointers(pointers);

		// Roles
		model.setRoles(input.getRoles());
		model.setType(input.getType());

		return model;
	}

}
