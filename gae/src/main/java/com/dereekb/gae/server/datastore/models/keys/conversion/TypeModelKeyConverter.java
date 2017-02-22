package com.dereekb.gae.server.datastore.models.keys.conversion;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * Converter used for converting {@link String} identifiers to their
 * {@link ModelKey} value safely.
 * <p>
 * The map is case-insensitive.
 *
 * @author dereekb
 *
 */
public interface TypeModelKeyConverter {

	public ModelKeyType typeForModelType(String modelType);

	public StringModelKeyConverter getConverterForType(String modelType) throws IllegalArgumentException;

	public ModelKey convertKey(String modelType,
	                           String value)
	        throws ConversionFailureException;

	public List<ModelKey> convertKeys(String modelType,
	                                  Collection<String> values)
	        throws ConversionFailureException;

}
