package com.dereekb.gae.utilities.json;

import java.lang.reflect.Type;

/**
 * Used for converting objects to JSON.
 *
 * @author dereekb
 *
 */
public interface JsonConverter {

	public String convertToJson(Object object);

	public <T> T convertToObject(String json,
	                             Class<T> type);

	public <T> T convertToObject(String json,
	                             Type type);

}
