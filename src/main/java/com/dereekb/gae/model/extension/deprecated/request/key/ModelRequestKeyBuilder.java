package com.dereekb.gae.model.extension.request.key;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Default implementation of {@link RequestKeyBuilder}. Uses a {@link String}
 * type and input model's {@link ModelKey} for generating a key for the request.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class ModelRequestKeyBuilder<T extends UniqueModel>
        implements RequestKeyBuilder<T> {

	/**
	 * Default format that separates the identifiers.
	 */
	public final static String DEFAULT_FORMAT = "%s.%s.%s";

	private final String type;
	private final String model;
	private final String format;

	public ModelRequestKeyBuilder(String type, String model) {
		this.type = type;
		this.model = model;
		this.format = DEFAULT_FORMAT;
	}

	public ModelRequestKeyBuilder(String type, String model, String format) {
		this.type = type;
		this.model = model;
		this.format = format;
	}

	public String getType() {
		return this.type;
	}

	public String getFormat() {
		return this.format;
	}

	@Override
	public String requestKeyForObject(T object) {
		ModelKey modelKey = object.getModelKey();
		String identifier = modelKey.keyAsString();
		String requestKey = String.format(this.format, this.type, this.model, identifier);
		return requestKey;
	}

}
