package com.dereekb.gae.server.datastore.models.keys.conversion;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

public interface TypeModelKeyConverter {

	public ModelKeyType typeForModelType(String modelType);

	public StringModelKeyConverter getConverterForType(String modelType) throws IllegalArgumentException;

	public ModelKey convertKey(String modelType,
	                           String value)
	        throws IllegalArgumentException;

	public List<ModelKey> convertKeys(String modelType,
	                                  Collection<String> values)
	        throws IllegalArgumentException;

}
