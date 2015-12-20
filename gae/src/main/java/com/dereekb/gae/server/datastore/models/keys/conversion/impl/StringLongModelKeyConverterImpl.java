package com.dereekb.gae.server.datastore.models.keys.conversion.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;

/**
 * {@link StringModelKeyConverter} implementation for converting a number value
 * within a {@link String} to a {@link ModelKey}.
 *
 * @author dereekb
 */
public class StringLongModelKeyConverterImpl
        implements StringModelKeyConverter {

	/**
	 * Statically available {@link StringLongModelKeyConverter} instance.
	 */
	public static final StringLongModelKeyConverterImpl CONVERTER = new StringLongModelKeyConverterImpl();

	// Bidirectional Converter
	@Override
	public List<ModelKey> convertTo(Collection<String> input) throws ConversionFailureException {
		return this.convert(input);
	}

	@Override
	public List<String> convertFrom(Collection<ModelKey> input) throws ConversionFailureException {
		List<String> keys = new ArrayList<String>();

		for (ModelKey modelKey : input) {
			Long id = modelKey.getId();

			if (id == null) {
				throw new ConversionFailureException("The ModelKey did not have an identifier set.");
			}

			keys.add(id.toString());
		}

		return keys;
	}

	// Directional Converter
	@Override
	public List<ModelKey> convert(Collection<String> input) throws ConversionFailureException {
		List<ModelKey> keys = new ArrayList<ModelKey>();

		try {
			for (String string : input) {
				ModelKey key = ModelKey.convertNumberString(string);
				keys.add(key);
			}
		} catch (NumberFormatException e) {
			throw new ConversionFailureException(e);
		}

		return keys;
	}

	// SingleDirectionalConverter
	@Override
	public ModelKey convertSingle(String input) throws ConversionFailureException {
		ModelKey key;

		try {
			key = ModelKey.convertNumberString(input);
		} catch (NumberFormatException e) {
			throw new ConversionFailureException(e);
		}

		return key;
	}

	// Support
	/**
	 * Safely converts the input value, returning {@code null} if the input
	 * equals {@code null} or the value cannot be converted.
	 *
	 * @param input
	 *            {@link String} containing the input.
	 * @return {@link ModelKey} with a {@link Long} identifier, or {@code null}
	 *         if the input was invalid.
	 */
	public ModelKey safeConvert(String input) {
		ModelKey key = null;

		if (input != null) {
			try {
				key = this.convertSingle(input);
			} catch (ConversionFailureException e) {

			}
		}

		return key;
	}

}
