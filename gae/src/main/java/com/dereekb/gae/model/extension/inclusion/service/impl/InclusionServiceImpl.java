package com.dereekb.gae.model.extension.inclusion.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.reader.InclusionReader;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;
import com.dereekb.gae.model.extension.inclusion.retriever.InclusionRetriever;
import com.dereekb.gae.model.extension.inclusion.service.InclusionRequest;
import com.dereekb.gae.model.extension.inclusion.service.InclusionResponse;
import com.dereekb.gae.model.extension.inclusion.service.InclusionService;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

/**
 * {@link InclusionService} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class InclusionServiceImpl<T>
        implements InclusionService<T> {

	private InclusionReader<T> reader;
	private InclusionRetriever retriever;

	public InclusionServiceImpl() {}

	public InclusionServiceImpl(InclusionReader<T> reader, InclusionRetriever retriever) {
		this.reader = reader;
		this.retriever = retriever;
	}

	public InclusionReader<T> getReader() {
		return this.reader;
	}

	public void setReader(InclusionReader<T> reader) {
		this.reader = reader;
	}

	public InclusionRetriever getRetriever() {
		return this.retriever;
	}

	public void setRetriever(InclusionRetriever retriever) {
		this.retriever = retriever;
	}

	@Override
	public InclusionResponse<T> loadRelated(InclusionRequest<T> request) {
		Collection<T> models = request.getTargets();
		InclusionReaderSetAnalysis<T> setAnalysis = this.reader.analyzeInclusions(models);

		Set<String> relatedTypes = new HashSet<String>(setAnalysis.getRelatedTypes());
		Set<String> relatedFilter = request.getTypeFilter();

		if (relatedFilter != null) {
			relatedTypes.retainAll(relatedFilter);
		}

		HashMapWithSet<String, ModelKey> relatedKeys = setAnalysis.getKeysForTypes(relatedTypes);

		// TODO complete.

		return new InclusionResponseImpl(request);
	}

	private class InclusionResponseImpl
	        implements InclusionResponse<T> {

		private final InclusionRequest<T> request;

		public InclusionResponseImpl(InclusionRequest<T> request) {
			this.request = request;
		}

	}

}
