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
 * Converter for building {@link ModelKey} using {@link Long} ids, or converting
 * between types.
 *
 * @author dereekb
 *
 */
public final class LongModelKeyConverter
        implements BidirectionalConverter<Long, ModelKey>, DirectionalConverter<Long, ModelKey>,
        SingleDirectionalConverter<Long, ModelKey> {

	// Bidirectional Converter
	@Override
	public List<ModelKey> convertTo(Collection<Long> input) throws ConversionFailureException {
		return this.convert(input);
	}

	@Override
	public List<Long> convertFrom(Collection<ModelKey> input) throws ConversionFailureException {
		List<Long> keys = new ArrayList<Long>();

		for (ModelKey modelKey : input) {
			Long id = modelKey.getId();

			if (id == null) {
				throw new ConversionFailureException("The ModelKey did not have a id set.");
			}

			keys.add(id);
		}

		return keys;
	}

	// Directional Converter
	@Override
	public List<ModelKey> convert(Collection<Long> input) throws ConversionFailureException {
		List<ModelKey> keys = new ArrayList<ModelKey>();

		try {
			for (Long id : input) {
				ModelKey key = new ModelKey(id);
				keys.add(key);
			}
		} catch (IllegalArgumentException e) {
			throw new ConversionFailureException(e.getMessage());
		}

		return keys;
	}

	// SingleDirectionalConverter
	@Override
	public ModelKey convertSingle(Long input) throws ConversionFailureException {
		ModelKey key;

		try {
			key = new ModelKey(input);
		} catch (NumberFormatException e) {
			throw new ConversionFailureException(e.getMessage());
		}

		return key;
	}

}
