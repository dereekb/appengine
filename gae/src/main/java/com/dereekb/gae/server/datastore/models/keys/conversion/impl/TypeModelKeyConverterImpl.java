package com.dereekb.gae.server.datastore.models.keys.conversion.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;

/**
 * Converter used for converting {@link String} identifiers to their
 * {@link ModelKey} value safely.
 * <p>
 * The map is case-insensitive.
 *
 * @author dereekb
 *
 */
public class TypeModelKeyConverterImpl
        implements TypeModelKeyConverter {

	private final static StringModelKeyConverter LONG_CONVERTER = StringLongModelKeyConverterImpl.CONVERTER;
	private final static StringModelKeyConverter STRING_CONVERTER = StringModelKeyConverterImpl.CONVERTER;

	private Map<String, ModelKeyType> map;

	public TypeModelKeyConverterImpl(Map<String, ModelKeyType> map) {
		this.map = map;
	}

	public final Map<String, ModelKeyType> getMap() {
		return this.map;
	}

	public final void setMap(Map<String, ModelKeyType> map) {
		this.map = new TreeMap<String, ModelKeyType>(String.CASE_INSENSITIVE_ORDER);

		if (map != null) {
			this.map.putAll(map);
		}
	}

	// MARK: Conversions
	public SingleDirectionalConverter<String, ModelKey> getSingleConverterForModelType(String modelType) {
		return this.getConverterForType(modelType);
	}

	public SingleDirectionalConverter<String, ModelKey> getSingleConverterForType(ModelKeyType type) {
		return this.getDirectionalConverterForType(type);
	}

	public StringModelKeyConverter getDirectionalConverterForType(ModelKeyType type) {
		StringModelKeyConverter converter = null;

		switch (type) {
			case NAME:
				converter = STRING_CONVERTER;
				break;
			case NUMBER:
				converter = LONG_CONVERTER;
				break;
			default:
				throw new IllegalArgumentException("Invalid type passed.");
		}

		return converter;
	}

	// MARK: TypeModelKeyConverter
	@Override
    public ModelKeyType typeForModelType(String modelType) {
		ModelKeyType type = this.map.get(modelType);

		if (type == null) {
			throw new IllegalArgumentException("Invalid type passed.");
		}

		return type;
	}

	@Override
	public StringModelKeyConverter getConverterForType(String modelType) throws IllegalArgumentException {
	    ModelKeyType type = this.typeForModelType(modelType);
		return this.getDirectionalConverterForType(type);
    }

	@Override
    public ModelKey convertKey(String modelType,
	                           String value) throws IllegalArgumentException {
		StringModelKeyConverter converter = this.getConverterForType(modelType);
		return converter.convertSingle(value);
	}

	@Override
    public List<ModelKey> convertKeys(String modelType,
	                                  Collection<String> values) throws IllegalArgumentException {
		StringModelKeyConverter converter = this.getConverterForType(modelType);
		return converter.convertTo(values);
	}

	@Override
	public String toString() {
		return "TypeModelKeyConverter [map=" + this.map + "]";
	}

}
