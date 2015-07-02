package com.dereekb.gae.model.extension.data.conversion.json;

import com.google.gson.reflect.TypeToken;

/**
 *
 /** Delegate required for converting byte data to a Java Object.
 *
 * A delegate is not required when converting data to JSON.
 *
 * @author dereekb
 *
 * @param <T> Type the JSON is converted to.
 */
public interface JsonByteDataConverterDelegate<T> {

	/**
	 * @return Returns a GSON {@link TypeToken} that defines what to use when
	 *         converting from a JSON string to an object.
	 */
	public TypeToken<T> getJsonConversionType();

}
