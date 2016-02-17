package com.dereekb.gae.server.datastore.models.keys.conversion;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for converting a {@link String} value to a {@link ModelKey} value.
 *
 * @author dereekb
 *
 */
public interface StringModelKeyConverter
        extends BidirectionalConverter<String, ModelKey>, DirectionalConverter<String, ModelKey>,
SingleDirectionalConverter<String, ModelKey> {

	/**
	 * Safely converts the input value, returning {@code null} if the input
	 * equals {@code null} or the value cannot be converted.
	 *
	 * @param input
	 *            {@link String} containing the input.
	 * @return {@link ModelKey} with a {@link Long} identifier, or {@code null}
	 *         if the input was invalid.
	 */
	public ModelKey safeConvert(String input);

}
