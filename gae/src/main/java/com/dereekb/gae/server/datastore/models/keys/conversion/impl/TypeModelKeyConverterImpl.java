package com.dereekb.gae.server.datastore.models.keys.conversion.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.exception.UnknownModelTypeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * {@link TypeModelKeyConverter} implementation.
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
		this.setMap(map);
	}

	public final Map<String, ModelKeyType> getMap() {
		return this.map;
	}

	public final void setMap(Map<String, ModelKeyType> map) {
		this.map = new CaseInsensitiveMap<ModelKeyType>(map);
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
	public ModelKeyType typeForModelType(String modelType) throws UnknownModelTypeException {
		ModelKeyType type = this.map.get(modelType);

		if (type == null) {
			throw new UnknownModelTypeException(modelType);
		}

		return type;
	}

	@Override
	public StringModelKeyConverter getConverterForType(String modelType) throws UnknownModelTypeException {
		ModelKeyType type = this.typeForModelType(modelType);
		return this.getDirectionalConverterForType(type);
	}

	@Override
	public ModelKey convertKey(String modelType,
	                           String value)
	        throws UnknownModelTypeException, ConversionFailureException {
		StringModelKeyConverter converter = this.getConverterForType(modelType);
		return converter.convertSingle(value);
	}

	@Override
	public List<ModelKey> convertKeys(String modelType,
	                                  Collection<String> values)
	        throws UnknownModelTypeException, ConversionFailureException {
		StringModelKeyConverter converter = this.getConverterForType(modelType);
		return converter.convertTo(values);
	}

	@Override
	public String toString() {
		return "TypeModelKeyConverter [map=" + this.map + "]";
	}

}
