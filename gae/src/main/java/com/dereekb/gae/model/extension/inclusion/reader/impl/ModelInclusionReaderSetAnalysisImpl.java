package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderSetAnalysis;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

/**
 * {@link InclusionReaderSetAnalysis} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelInclusionReaderSetAnalysisImpl<T extends UniqueModel>
        implements ModelInclusionReaderSetAnalysis<T> {

	private final Collection<T> models;
	private final InclusionReaderSetAnalysisImplDelegate<T> delegate;

	public ModelInclusionReaderSetAnalysisImpl(Collection<T> models, InclusionReaderSetAnalysisImplDelegate<T> delegate) {
		this.models = models;
		this.delegate = delegate;
	}

	// MARK: ModelInclusionReaderSetAnalysis
	@Override
	public Collection<T> getAnalyzedModels() {
		return this.models;
	}

	// MARK: InclusionReaderSetAnalysis
	@Override
	public Collection<ModelKey> getAnalyzedModelKeys() {
		return ModelKey.readModelKeys(this.models);
	}

	@Override
	public Set<String> getRelatedTypes() {
		return this.delegate.getRelatedTypes();
	}

	@Override
	public Set<ModelKey> getKeysForType(String type) throws InclusionTypeUnavailableException {
		Set<ModelKey> keys = new HashSet<ModelKey>();

		for (T model : this.models) {
			Collection<ModelKey> relatedKeys = this.delegate.getRelatedKeysForType(type, model);
			keys.addAll(relatedKeys);
		}

		return keys;
	}

	@Override
	public HashMapWithSet<String, ModelKey> getKeysForTypes(Set<String> types) throws InclusionTypeUnavailableException {
		HashMapWithSet<String, ModelKey> keys = new HashMapWithSet<String, ModelKey>();

		for (T model : this.models) {
			Map<String, Set<ModelKey>> relatedKeys = this.delegate.getRelatedKeysMap(model);
			keys.addAll(relatedKeys);
		}

		return keys;
	}

	@Override
	public String toString() {
		return "InclusionReaderSetAnalysisImpl [models=" + this.models + ", delegate=" + this.delegate + "]";
	}

}
