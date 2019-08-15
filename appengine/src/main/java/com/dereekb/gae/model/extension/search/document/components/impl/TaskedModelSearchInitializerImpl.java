package com.dereekb.gae.model.extension.search.document.components.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.components.ModelSearch;
import com.dereekb.gae.model.extension.search.document.components.ModelSearchFactory;
import com.dereekb.gae.model.extension.search.document.components.ModelSearchInitializer;
import com.dereekb.gae.server.search.request.SearchServiceQueryOptions;
import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;
import com.dereekb.gae.server.search.request.impl.SearchServiceQueryRequestImpl;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;
import com.dereekb.gae.utilities.task.Task;

/**
 * {@link ModelSearchInitializer} implementation that allows a {@link Task} to
 * modify the generated search before returning.
 *
 * @author dereekb
 *
 */
public class TaskedModelSearchInitializerImpl<Q extends ModelSearch>
        implements ModelSearchInitializer<Q> {

	private ModelSearchFactory<Q> modelSearchFactory;
	private Task<Q> modifySearchTask;

	public TaskedModelSearchInitializerImpl(ModelSearchFactory<Q> modelSearchFactory, Task<Q> modifySearchTask) {
		super();
		this.setModelSearchFactory(modelSearchFactory);
		this.setModifySearchTask(modifySearchTask);
	}

	public ModelSearchFactory<Q> getModelSearchFactory() {
		return this.modelSearchFactory;
	}

	public void setModelSearchFactory(ModelSearchFactory<Q> modelSearchFactory) {
		if (modelSearchFactory == null) {
			throw new IllegalArgumentException("modelSearchFactory cannot be null.");
		}

		this.modelSearchFactory = modelSearchFactory;
	}

	public Task<Q> getModifySearchTask() {
		return this.modifySearchTask;
	}

	public void setModifySearchTask(Task<Q> modifySearchTask) {
		if (modifySearchTask == null) {
			throw new IllegalArgumentException("modifySearchTask cannot be null.");
		}

		this.modifySearchTask = modifySearchTask;
	}

	// MARK: ModelSearchInitializer
	@Override
	public SearchServiceQueryRequest initalizeSearchRequest(String index,
	                                                        SearchServiceQueryOptions searchOptions,
	                                                        Map<String, String> parameters)
	        throws IllegalQueryArgumentException {

		Q search = this.modelSearchFactory.makeSearch(parameters);

		this.modifySearchTask.doTask(search);

		return new SearchServiceQueryRequestImpl(index, searchOptions, search);
	}

	@Override
	public String toString() {
		return "TaskedModelSearchInitializerImpl [modelSearchFactory=" + this.modelSearchFactory + ", modifySearchTask="
		        + this.modifySearchTask + "]";
	}

}
