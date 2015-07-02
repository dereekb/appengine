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
 * Used for converting strings to {@link ModelKey} instances with number
 * identifiers.
 *
 * @author dereekb
 */
public final class StringLongModelKeyConverter
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
				ModelKey key = ModelKey.convertStringNumber(string);
				keys.add(key);
			}
		} catch (NumberFormatException e) {
			throw new ConversionFailureException("Could not convert string number.");
		}

		return keys;
	}

	// SingleDirectionalConverter
	@Override
	public ModelKey convertSingle(String input) throws ConversionFailureException {
		ModelKey key;

		try {
			key = ModelKey.convertStringNumber(input);
		} catch (NumberFormatException e) {
			throw new ConversionFailureException("Could not convert string number.");
		}

		return key;
	}

}
