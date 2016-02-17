package com.dereekb.gae.model.extension.inclusion.retriever.impl;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.retriever.InclusionRetrieverInput;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

/**
 * {@link InclusionRetrieverInput} implementation that uses a
 * {@link HashMapWithSet}.
 *
 * @author dereekb
 *
 */
public class InclusionRetrieverInputImpl
        implements InclusionRetrieverInput {

	private final HashMapWithSet<String, ModelKey> map;

	public InclusionRetrieverInputImpl(HashMapWithSet<String, ModelKey> map) {
		this.map = map;
	}

	// MARK: InclusionRetrieverInput
	@Override
	public Set<String> getTargetTypes() {
		return this.map.keySet();
	}

	@Override
	public List<ModelKey> getTargetKeysForType(String type) {
		return this.map.valuesForKey(type);
	}

	@Override
	public String toString() {
		return "InclusionRetrieverInputImpl [map=" + this.map + "]";
	}

}
