package com.dereekb.gae.model.extension.inclusion.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReader;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;
import com.dereekb.gae.model.extension.inclusion.retriever.InclusionRetriever;
import com.dereekb.gae.model.extension.inclusion.retriever.InclusionRetrieverOutput;
import com.dereekb.gae.model.extension.inclusion.retriever.impl.InclusionRetrieverInputImpl;
import com.dereekb.gae.model.extension.inclusion.service.InclusionRequest;
import com.dereekb.gae.model.extension.inclusion.service.InclusionResponse;
import com.dereekb.gae.model.extension.inclusion.service.InclusionService;
import com.dereekb.gae.server.datastore.models.UniqueModel;
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
		return new InclusionResponseImpl(request);
	}

	/**
	 * Internal implementation of {@link InclusionResponse} for
	 * {@link InclusionServiceImpl}.
	 *
	 * @author dereekb
	 */
	private class InclusionResponseImpl
	        implements InclusionResponse<T> {

		private final InclusionRequest<T> request;

		private InclusionReaderSetAnalysis<T> analysis = null;

		private Set<String> relatedTypes = null;
		private HashMapWithSet<String, ModelKey> relatedKeys = null;

		private InclusionRetrieverOutput retrieverOutput = null;
		private Map<String, Collection<? extends UniqueModel>> loaded = new HashMap<>();

		public InclusionResponseImpl(InclusionRequest<T> request) {
			this.request = request;
		}

		public InclusionReaderSetAnalysis<T> getAnalysis() {
			if (this.analysis == null) {
				this.analysis = this.buildAnalysis();
			}

			return this.analysis;
		}

		public Set<String> getRelatedTypes() {
			if (this.relatedTypes == null) {
				this.relatedTypes = this.buildRelatedTypes();
			}

			return this.relatedTypes;
		}

		public HashMapWithSet<String, ModelKey> getRelatedKeys() {
			if (this.relatedKeys == null) {
				this.relatedKeys = this.buildRelatedKeys();
			}

			return this.relatedKeys;
		}

		public InclusionRetrieverOutput getRetrieverOutput() {
			if (this.retrieverOutput == null) {
				this.retrieverOutput = this.buildRetrieverOutput();
			}

			return this.retrieverOutput;
		}

		// MARK: InclusionResponse
		@Override
		public Set<String> getAvailableTypes() {
			return this.getRelatedTypes();
		}

		@Override
		public Collection<? extends UniqueModel> getRelated(String type) throws InclusionTypeUnavailableException {
			Set<String> relatedTypes = this.getRelatedTypes();

			if (relatedTypes.contains(type) == false) {
				throw new InclusionTypeUnavailableException(type);
			}

			Collection<? extends UniqueModel> related = this.loaded.get(type);

			if (related == null) {
				related = this.loadRelatedType(type);
			}

			return related;
		}

		@Override
		public Map<String, Collection<? extends UniqueModel>> getAllRelated() {
			this.loadRemainingTypes();
			return new HashMap<>(this.loaded);
		}

		// MARK: Internal
		private void loadRemainingTypes() {
			if (this.loaded.isEmpty()) {
				InclusionRetrieverOutput output = this.getRetrieverOutput();
				this.loaded = output.getAllRelatedModels();
			} else {
				Set<String> unloaded = this.getUnloadedTypes();

				if (unloaded.isEmpty() == false) {
					for (String type : unloaded) {
						this.loadRelatedType(type);
					}
				}
			}
		}

		private Collection<? extends UniqueModel> loadRelatedType(String type) {
			InclusionRetrieverOutput output = this.getRetrieverOutput();
			Collection<? extends UniqueModel> related = output.getRelatedModels(type);
			this.loaded.put(type, related);
			return related;
		}

		private Set<String> getUnloadedTypes() {
			Set<String> remaining = new HashSet<String>(this.getRelatedTypes());
			Set<String> loaded = this.loaded.keySet();
			remaining.removeAll(loaded);
			return remaining;
		}

		private InclusionRetrieverOutput buildRetrieverOutput() {
			HashMapWithSet<String, ModelKey> relatedKeys = this.getRelatedKeys();
			InclusionRetrieverInputImpl input = new InclusionRetrieverInputImpl(relatedKeys);
			return InclusionServiceImpl.this.retriever.retrieveRelated(input);
		}

		private InclusionReaderSetAnalysis<T> buildAnalysis() {
			Collection<T> models = this.request.getTargets();
			return InclusionServiceImpl.this.reader.analyzeInclusions(models);
		}

		private HashMapWithSet<String, ModelKey> buildRelatedKeys() {
			InclusionReaderSetAnalysis<T> analysis = this.getAnalysis();
			HashMapWithSet<String, ModelKey> relatedKeys = analysis.getKeysForTypes(this.relatedTypes);
			return relatedKeys;
		}

		private Set<String> buildRelatedTypes() {
			InclusionReaderSetAnalysis<T> analysis = this.getAnalysis();
			Set<String> relatedTypes = new HashSet<String>(analysis.getRelatedTypes());
			Set<String> relatedFilter = this.request.getTypeFilter();

			if (relatedFilter != null) {
				relatedTypes.retainAll(relatedFilter);
			}

			return relatedTypes;
		}

	}

}
