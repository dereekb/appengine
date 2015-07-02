package com.dereekb.gae.server.datastore.models.keys.conversion;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * Converter used for converting keys from string keyed by model name types.
 *
 * @author dereekb
 *
 */
public class ModelKeyTypeConverter {

	private final static StringLongModelKeyConverter stringLongConverter = new StringLongModelKeyConverter();
	private final static StringModelKeyConverter stringConverter = new StringModelKeyConverter();

	private Map<String, ModelKeyType> map;

	public ModelKeyTypeConverter(Map<String, ModelKeyType> map) {
		this.map = map;
	}

	public Map<String, ModelKeyType> getMap() {
		return this.map;
	}

	public void setMap(Map<String, ModelKeyType> map) {
		this.map = map;
	}

	public SingleDirectionalConverter<String, ModelKey> getSingleConverterForModelType(String modelType) {
		ModelKeyType type = this.map.get(modelType);
		return this.getSingleConverterForType(type);
	}

	public SingleDirectionalConverter<String, ModelKey> getSingleConverterForType(ModelKeyType type) {
		SingleDirectionalConverter<String, ModelKey> converter = null;

		switch (type) {
			case NAME:
				converter = stringConverter;
				break;
			case NUMBER:
				converter = stringLongConverter;
				break;
			default:
				throw new IllegalArgumentException("Invalid type passed.");
		}

		return converter;
	}

	public DirectionalConverter<String, ModelKey> getDirectionalConverterForModelType(String modelType) {
		ModelKeyType type = this.map.get(modelType);
		return this.getDirectionalConverterForType(type);
	}

	public DirectionalConverter<String, ModelKey> getDirectionalConverterForType(ModelKeyType type) {
		DirectionalConverter<String, ModelKey> converter = null;

		switch (type) {
			case NAME:
				converter = stringConverter;
				break;
			case NUMBER:
				converter = stringLongConverter;
				break;
			default:
				throw new IllegalArgumentException("Invalid type passed.");
		}

		return converter;
	}

	public ModelKeyType typeForModelType(String modelType) {
		ModelKeyType type = this.map.get(modelType);

		if (type == null) {
			throw new IllegalArgumentException("Invalid type passed.");
		}

		return type;
	}

	public ModelKey convertKey(String modelType,
	                           String value) {

		ModelKey key = null;
		ModelKeyType type = this.typeForModelType(modelType);

		switch (type) {
			case NAME:
				key = stringConverter.convertSingle(value);
				break;
			case NUMBER:
				key = stringLongConverter.convertSingle(value);
				break;
			default:
				throw new IllegalArgumentException("Unknown model type passed.");
		}

		return key;
	}

	public List<ModelKey> convertKeys(String modelType,
	                                  Collection<String> values) {

		List<ModelKey> keys;
		ModelKeyType type = this.typeForModelType(modelType);

		switch (type) {
			case NAME:
				keys = stringConverter.convert(values);
				break;
			case NUMBER:
				keys = stringLongConverter.convert(values);
				break;
			default:
				throw new IllegalArgumentException("Unknown model type passed.");
		}

		return keys;
	}

}
