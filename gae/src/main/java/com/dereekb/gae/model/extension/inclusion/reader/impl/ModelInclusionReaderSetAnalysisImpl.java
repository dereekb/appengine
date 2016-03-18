package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReader;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderSetAnalysis;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

/**
 * {@link ModelInclusionReaderSetAnalysis} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelInclusionReaderSetAnalysisImpl<T extends UniqueModel>
        implements ModelInclusionReaderSetAnalysis<T> {

	private Collection<T> models;
	private HashMapWithSet<String, ModelKey> relatedKeys;

	public static <T extends UniqueModel> ModelInclusionReaderSetAnalysis<T> makeSetAnalysis(Collection<T> models,
	                                                                                         ModelInclusionReader<T> reader) {
		List<ModelInclusionReaderAnalysis<T>> analysisList = new ArrayList<ModelInclusionReaderAnalysis<T>>();

		for (T model : models) {
			ModelInclusionReaderAnalysis<T> analysis = reader.analyzeInclusionsForModel(model);
			analysisList.add(analysis);
		}

		return ModelInclusionReaderSetAnalysisImpl.makeSetAnalysis(analysisList);
	}

	public static <T extends UniqueModel> ModelInclusionReaderSetAnalysis<T> makeSetAnalysis(Collection<ModelInclusionReaderAnalysis<T>> analysisList) {
		List<T> models = new ArrayList<T>();
		HashMapWithSet<String, ModelKey> keys = new HashMapWithSet<String, ModelKey>();

		for (ModelInclusionReaderAnalysis<T> analysis : analysisList) {
			T model = analysis.getAnalyzedModel();
			models.add(model);

			HashMapWithSet<String, ModelKey> relationMap = analysis.getRelationMap();
			keys.addAll(relationMap);
		}

		return new ModelInclusionReaderSetAnalysisImpl<T>(models, keys);
	}

	private ModelInclusionReaderSetAnalysisImpl(Collection<T> models, HashMapWithSet<String, ModelKey> relatedKeys) {
		this.models = models;
		this.relatedKeys = relatedKeys;
	}

	// MARK: ModelInclusionReaderSetAnalysis
	@Override
    public Collection<ModelKey> getModelKeys() {
		return ModelKey.readModelKeys(this.models);
    }

	@Override
    public Set<String> getRelatedTypes() {
		return this.relatedKeys.keySet();
    }

	@Override
    public Set<ModelKey> getKeysForType(String type) throws InclusionTypeUnavailableException {
		return this.relatedKeys.get(type);
    }

	@Override
    public Collection<T> getAnalyzedModels() {
		return this.models;
    }

	@Override
	public String toString() {
		return "ModelInclusionReaderSetAnalysisImpl [models=" + this.models + ", relatedKeys=" + this.relatedKeys + "]";
	}

}
