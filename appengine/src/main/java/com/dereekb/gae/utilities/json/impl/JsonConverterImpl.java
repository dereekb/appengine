package com.dereekb.gae.utilities.json.impl;

import java.lang.reflect.Type;

import com.dereekb.gae.utilities.json.JsonConverter;
import com.dereekb.gae.utilities.json.JsonOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * {@link JsonConverter} implementation using GSON.
 *
 * @author dereekb
 *
 */
public class JsonConverterImpl
        implements JsonConverter {

	private JsonOptions options;

	public JsonConverterImpl() {
		this(new JsonOptionsImpl());
	}

	public JsonConverterImpl(JsonOptions options) {
		this.options = options;
	}

	public JsonOptions getOptions() {
		return this.options;
	}

	public void setOptions(JsonOptions options) {
		if (options == null) {
			throw new IllegalArgumentException("options cannot be null.");
		}

		this.options = options;
	}

	// MARK: Converter
	@Override
	public String convertToJson(Object object) {
		Gson gson = this.getGson();
		String result = gson.toJson(object);
		return result;
	}

	@Override
	public <T> T convertToObject(String json,
	                             Class<T> type) {
		Gson gson = this.getGson();
		T result = gson.fromJson(json, type);
		return result;
	}

	@Override
	public <T> T convertToObject(String json,
	                             Type type) {
		Gson gson = this.getGson();
		T result = gson.fromJson(json, type);
		return result;
	}

	// MARK: Internal
	private GsonBuilder makeGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();

		if (this.options.getPrettyPrinting()) {
			builder.setPrettyPrinting();
		}

		if (this.options.getSerializeNulls()) {
			builder.serializeNulls();
		}

		// TODO: Implement additional JSON options?
		// builder.registerTypeAdapter(BlobKey.class, new
		// GsonBlobKeyDeserializer());

		return builder;
	}

	private Gson getGson() {
		GsonBuilder builder = this.makeGsonBuilder();
		return builder.create();
	}

}
