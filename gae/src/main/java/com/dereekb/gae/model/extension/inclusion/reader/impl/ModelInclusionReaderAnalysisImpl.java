package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderAnalysis;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

/**
 * {@link ModelInclusionReaderAnalysis} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelInclusionReaderAnalysisImpl<T extends UniqueModel>
        implements ModelInclusionReaderAnalysis<T> {

	private T model;
	private HashMapWithSet<String, ModelKey> relatedKeys;

	public ModelInclusionReaderAnalysisImpl(T model, HashMapWithSet<String, ModelKey> relatedKeys) {
		this.model = model;
		this.relatedKeys = relatedKeys;
	}

	// MARK: ModelInclusionReaderAnalysis
	@Override
	public ModelKey getModelKey() {
		return this.model.getModelKey();
	}

	@Override
	public Set<String> getRelatedTypes() {
		return this.relatedKeys.keySet();
	}

	@Override
	public Collection<ModelKey> getKeysForType(String type) throws InclusionTypeUnavailableException {
		return this.relatedKeys.get(type);
	}

	@Override
	public HashMapWithSet<String, ModelKey> getRelationMap() {
		return this.relatedKeys;
	}

	@Override
	public T getAnalyzedModel() {
		return this.model;
	}

	@Override
	public String toString() {
		return "ModelInclusionReaderAnalysisImpl [model=" + this.model + ", relatedKeys=" + this.relatedKeys + "]";
	}

}
