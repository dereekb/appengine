package com.dereekb.gae.utilities.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {

	private JsonOptions options;

	public JsonConverter() {
		this.options = new JsonOptions();
	}

	public JsonConverter(JsonOptions options) {
		this.options = options;
	}

	private GsonBuilder makeGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();

		if (this.options.getPrettyPrinting()) {
			builder.setPrettyPrinting();
		}

		if (this.options.getSerializeNulls()) {
			builder.serializeNulls();
		}

		// TODO: Implement additional JSON options?
		// builder.registerTypeAdapter(BlobKey.class, new GsonBlobKeyDeserializer());

		return builder;
	}

	private Gson getGson() {
		GsonBuilder builder = this.makeGsonBuilder();
		return builder.create();
	}

	public String convertToJson(Object object) {
		Gson gson = this.getGson();
		String result = gson.toJson(object);
		return result;
	}

	public <T> T convertToObject(String json,
	                             Class<T> type) {
		Gson gson = this.getGson();
		T result = gson.fromJson(json, type);
		return result;
	}

	public <T> T convertToObject(String json,
	                             Type type) {
		Gson gson = this.getGson();
		T result = gson.fromJson(json, type);
		return result;
	}

	public JsonOptions getOptions() {
		return this.options;
	}

	public void setOptions(JsonOptions options) {
		this.options = options;
	}
}
