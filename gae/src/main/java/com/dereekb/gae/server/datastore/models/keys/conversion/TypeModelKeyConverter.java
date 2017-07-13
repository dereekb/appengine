package com.dereekb.gae.server.datastore.models.keys.conversion;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.exception.UnknownModelTypeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * Converter used for converting {@link String} identifiers to their
 * {@link ModelKey} value safely.
 * <p>
 * The type map is case-insensitive.
 *
 * @author dereekb
 *
 */
public interface TypeModelKeyConverter {

	/**
	 * Returns the {@link ModelKeyType} for the requested type.
	 * 
	 * @param modelType {@link String}. Never {@code null}.
	 * @return {@link ModelKeyType}. Never {@code null}.
	 * @throws UnknownModelTypeException thrown if the input model type is unknown.
	 */
	public ModelKeyType typeForModelType(String modelType) throws UnknownModelTypeException;

	/**
	 * Returns a string key converter for the requested type.
	 * 
	 * @param modelType {@link String}. Never {@code null}.
	 * @return {@link StringModelKeyConverter}. Never {@code null}.
	 * @throws UnknownModelTypeException thrown if the input model type is unknown.
	 */
	public StringModelKeyConverter getConverterForType(String modelType) throws UnknownModelTypeException;

	/**
	 * Converts the input key.
	 * 
	 * @param modelType {@link String}. Never {@code null}.
	 * @param key {@link String}. Never {@code null}. 
	 * @return {@link ModelKey}. Never {@code null}.
	 * @throws UnknownModelTypeException thrown if the input model type is unknown.
	 * @throws ConversionFailureException thrown if the conversion fails.
	 */
	public ModelKey convertKey(String modelType,
	                           String key)
	        throws UnknownModelTypeException, ConversionFailureException;

	/**
	 * Converts the input keys.
	 * 
	 * @param modelType {@link String}. Never {@code null}.
	 * @param keys {@link Collection}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 * @throws UnknownModelTypeException thrown if the input model type is unknown.
	 * @throws ConversionFailureException thrown if the conversion fails.
	 */
	public List<ModelKey> convertKeys(String modelType,
	                                  Collection<String> keys)
	        throws UnknownModelTypeException, ConversionFailureException;

}
