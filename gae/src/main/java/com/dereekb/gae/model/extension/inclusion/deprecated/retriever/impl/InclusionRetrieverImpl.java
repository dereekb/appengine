package com.dereekb.gae.model.extension.inclusion.retriever.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.model.extension.inclusion.retriever.InclusionRetriever;
import com.dereekb.gae.model.extension.inclusion.retriever.InclusionRetrieverInput;
import com.dereekb.gae.model.extension.inclusion.retriever.InclusionRetrieverOutput;
import com.dereekb.gae.model.extension.read.anonymous.AnonymousModelReader;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link InclusionRetriever} implementation.
 *
 * @author dereekb
 *
 */
@Deprecated
public class InclusionRetrieverImpl
        implements InclusionRetriever {

	private AnonymousModelReader reader;

	@Override
	public InclusionRetrieverOutput retrieveRelated(InclusionRetrieverInput input) {
		return new OutputImpl(input);
	}

	public AnonymousModelReader getReader() {
		return this.reader;
	}

	public void setReader(AnonymousModelReader reader) {
		this.reader = reader;
	}

	/**
	 * Internal {@link InclusionRetrieverOutput} implementation.
	 *
	 * @author dereekb
	 *
	 */
	private class OutputImpl
	        implements InclusionRetrieverOutput {

		private final InclusionRetrieverInput input;

		public OutputImpl(InclusionRetrieverInput input) {
			this.input = input;
		}

		@Override
		public Collection<? extends UniqueModel> getRelatedModels(String type) throws InclusionTypeUnavailableException {
			Collection<? extends UniqueModel> models;
			List<ModelKey> keys = this.input.getTargetKeysForType(type);

			if (keys == null || keys.isEmpty()) {
				models = Collections.emptyList();
			} else {
				ReadResponse<? extends UniqueModel> response = InclusionRetrieverImpl.this.reader.read(type, keys);
				models = response.getModels();
			}

			return models;
		}

		@Override
		public Map<String, Collection<? extends UniqueModel>> getAllRelatedModels() {
			Set<String> types = this.input.getTargetTypes();
			Map<String, Collection<? extends UniqueModel>> map = new HashMap<String, Collection<? extends UniqueModel>>();

			for (String type : types) {
				Collection<? extends UniqueModel> results = this.getRelatedModels(type);
				map.put(type, results);
			}

			return map;
		}

	}

}
