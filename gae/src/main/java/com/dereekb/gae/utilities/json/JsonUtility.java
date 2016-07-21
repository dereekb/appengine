package com.dereekb.gae.utilities.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtility {

	public static String getString(JsonElement element,
	                               String key) {
		return new JsonObjectReader(element).getString(key);
	}

	public static Boolean getBoolean(JsonElement element,
	                                 String key) {
		return new JsonObjectReader(element).getBoolean(key);
	}

	// MARK: Reader
	public static JsonObjectReader getReader(JsonElement element) {
		return new JsonObjectReader(element);
	}

	public static class JsonObjectReader {

		private JsonObject object;

		public JsonObjectReader(JsonObject element) {
			this.setObject(element);
		}

		public JsonObjectReader(JsonElement element) throws IllegalStateException {
			this.setObject(element.getAsJsonObject());
		}

		public JsonObject getElement() {
			return this.object;
		}

		public void setObject(JsonObject object) {
			if (object == null) {
				throw new IllegalArgumentException();
			}

			this.object = object;
		}

		// MARK: Accessors
		public String getString(String key) {
			JsonElement element = this.getElement(key);
			String string = null;

			if (element != null) {
				string = element.getAsString();
			}

			return string;
		}

		public Boolean getBoolean(String key) {
			JsonElement element = this.getElement(key);
			Boolean bool = null;

			if (element != null) {
				bool = element.getAsBoolean();
			}

			return bool;
		}

		private JsonElement getElement(String key) {
			return this.object.get(key);
		}

	}

}
