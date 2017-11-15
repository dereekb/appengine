package com.dereekb.gae.server.datastore.models.impl;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.read.exception.UnavailableTypesException;
import com.dereekb.gae.server.datastore.models.ModelUtility;
import com.dereekb.gae.server.datastore.models.TypedModel;

/**
 * Abstract class that contains {@link TypedModel} values and puts them into a
 * map based on their type.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractTypedModelMap<T extends TypedModel> {

	private Map<String, T> typeMap;

	public Map<String, T> getTypeMap() {
		return this.typeMap;
	}

	public AbstractTypedModelMap(List<T> typeMap) {
		this.setTypeMap(typeMap);
	}

	public void setTypeMap(List<T> typeMap) {
		Map<String, T> map = ModelUtility.makeTypedModelMap(typeMap);
		this.setTypeMap(map);
	}

	public void setTypeMap(Map<String, T> typeMap) {
		if (typeMap == null) {
			throw new IllegalArgumentException("typeMap cannot be null.");
		}

		this.typeMap = typeMap;
	}
	
	protected T getEntryForType(String type) {
		T entry = this.typeMap.get(type);
		
		if (entry == null) {
			this.throwEntryDoesntExistException(type);
		}
		
		return entry;
	}
	
	protected void throwEntryDoesntExistException(String type) throws RuntimeException {
		throw new UnavailableTypesException(type);
	}
	
	@Override
	public String toString() {
		return "AbstractTypedModelMap [typeMap=" + this.typeMap + "]";
	}

}
