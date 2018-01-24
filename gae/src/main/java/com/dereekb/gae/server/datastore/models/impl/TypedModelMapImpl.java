package com.dereekb.gae.server.datastore.models.impl;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.read.exception.UnavailableTypesException;
import com.dereekb.gae.server.datastore.models.ModelUtility;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.TypedModelMap;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.utilities.collections.map.impl.CaseInsensitiveEntryContainerImpl;

/**
 * Abstract class that contains {@link TypedModel} values and puts them into a
 * map based on their type.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TypedModelMapImpl<T extends TypedModel> extends CaseInsensitiveEntryContainerImpl<T>
        implements TypedModelMap<T> {

	public TypedModelMapImpl(List<T> typeMap) {
		this.setTypeMap(typeMap);
	}

	public Map<String, T> getTypeMap() {
		return this.getEntries();
	}

	public final void setTypeMap(List<T> typeMap) {
		if (typeMap == null) {
			throw new IllegalArgumentException("typeMap cannot be null.");
		}

		this.setTypeMap(ModelUtility.makeTypedModelMap(typeMap));
	}

	public final void setTypeMap(Map<String, T> typeMap) {
		if (typeMap == null) {
			throw new IllegalArgumentException("typeMap cannot be null.");
		}

		this.setTypeMap(new CaseInsensitiveMap<T>(typeMap));
	}

	protected void setTypeMap(CaseInsensitiveMap<T> typeMap) {
		this.setEntries(typeMap);
	}

	// MARK: TypedModelMap
	@Override
	public T getEntryForType(TypedModel model) throws RuntimeException {
		return this.getEntryForType(model.getModelType());
	}

	@Override
	protected void throwEntryDoesntExistException(String type) throws RuntimeException {
		throw new UnavailableTypesException(type);
	}

	@Override
	public String toString() {
		return "TypedModelMapImpl [typeMap=" + this.getEntries() + "]";
	}

}
