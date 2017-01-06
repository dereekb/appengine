package com.dereekb.gae.server.auth.model.key.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.utilities.misc.reader.DateLongConverter;
import com.googlecode.objectify.Key;

/**
 * {@link DirectionalConverter} for converting a {@link LoginKey} to
 * {@link LoginKeyData}.
 *
 * @author dereekb
 */
public final class LoginKeyDataReader extends AbstractDirectionalConverter<LoginKeyData, LoginKey> {

	private static final StringModelKeyConverter KEY_CONVERTER = StringLongModelKeyConverterImpl.CONVERTER;

	private static final ObjectifyKeyUtility<LoginPointer> LOGIN_POINTER_KEY_UTIL = ObjectifyKeyUtility
	        .make(LoginPointer.class);

	@Override
	public LoginKey convertSingle(LoginKeyData input) throws ConversionFailureException {
		LoginKey model = new LoginKey();

		// Identifier
		String stringIdentifier = input.getKey();
		model.setModelKey(KEY_CONVERTER.safeConvert(stringIdentifier));

		Long date = input.getCreated();
		model.setCreated(DateLongConverter.CONVERTER.safeConvert(date));

		// Pointers
		Key<LoginPointer> pointer = LOGIN_POINTER_KEY_UTIL.keyFromString(input.getPointer());
		model.setPointer(pointer);

		// Data
		model.setName(input.getName());
		model.setMask(input.getMask());
		model.setVerification(input.getVerification());
		model.setExpiration(input.getExpiration());

		return model;
	}

}
