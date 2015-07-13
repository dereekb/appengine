package com.dereekb.gae.server.datastore.models.keys.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Converter for building {@link ModelKey} using string names, or converting
 * between types.
 *
 * @author dereekb
 *
 */
public final class StringModelKeyConverter
        implements BidirectionalConverter<String, ModelKey>, DirectionalConverter<String, ModelKey>,
        SingleDirectionalConverter<String, ModelKey> {

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

		for (String string : input) {
			ModelKey key = new ModelKey(string);
			keys.add(key);
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
			throw new ConversionFailureException(e.getMessage());
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
