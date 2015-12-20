package com.dereekb.gae.server.datastore.models.keys.conversion.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;

/**
 * Converter for building {@link ModelKey} using string names, or converting
 * between types.
 *
 * @author dereekb
 *
 */
public class StringModelKeyConverterImpl
        implements StringModelKeyConverter {

	/**
	 * Statically available {@link StringModelKeyConverter} instance.
	 */
	public static final StringModelKeyConverter CONVERTER = new StringModelKeyConverterImpl();

	// Bidirectional Converter
	@Override
	public List<ModelKey> convertTo(Collection<String> input) throws ConversionFailureException {
		return this.convert(input);
	}

	@Override
	public List<String> convertFrom(Collection<ModelKey> input) throws ConversionFailureException {
		List<String> keys = new ArrayList<String>();

		for (ModelKey modelKey : input) {
			String name = modelKey.getName();

			if (name == null) {
				throw new ConversionFailureException("The ModelKey did not have a name set.");
			}

			keys.add(name);
		}

		return keys;
	}

	// Directional Converter
	@Override
	public List<ModelKey> convert(Collection<String> input) throws ConversionFailureException {
		List<ModelKey> keys = new ArrayList<ModelKey>();

		try {
			for (String string : input) {
				ModelKey key = new ModelKey(string);
				keys.add(key);
			}
		} catch (IllegalArgumentException e) {
			throw new ConversionFailureException(e);
		}

		return keys;
	}

	// SingleDirectionalConverter
	@Override
	public ModelKey convertSingle(String input) throws ConversionFailureException {
		ModelKey key;

		try {
			key = new ModelKey(input);
		} catch (IllegalArgumentException e) {
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
	 * @return {@link ModelKey} with a {@link String} name, or {@code null} if
	 *         the input was invalid.
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
