package com.dereekb.gae.model.crud.services.components.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.options.CreateRequestOptions;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.impl.CreateResponseImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * Default implementation of {@link CreateService} using a {@link IterableTask}
 * with {@link CreatePair} to create models.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class CreateServiceImpl<T extends UniqueModel>
        implements CreateService<T> {

	private IterableTask<CreatePair<T>> createTask;

	public CreateServiceImpl(IterableTask<CreatePair<T>> createTask) {
		this.createTask = createTask;
	}

	public IterableTask<CreatePair<T>> getCreateTask() {
		return this.createTask;
	}

	public void setCreateTask(IterableTask<CreatePair<T>> createTask) {
		this.createTask = createTask;
	}

	@Override
	public CreateResponse<T> create(CreateRequest<T> request) throws AtomicOperationException {
		CreateResponse<T> createResponse = null;

		Collection<T> templates = request.getTemplates();
		CreateRequestOptions options = request.getOptions();

		List<CreatePair<T>> pairs = CreatePair.createPairsForModels(templates);

		try {
			this.createTask.doTask(pairs);

			HashMapWithList<FilterResult, CreatePair<T>> results = ResultsPair.filterSuccessfulPairs(pairs);
			List<CreatePair<T>> errorPairs = results.getElements(FilterResult.FAIL);
			List<T> errorTemplates = CreatePair.getKeys(errorPairs);

			if (errorTemplates.size() > 0 && options.isAtomic()) {
				throw new AtomicOperationException(errorTemplates, AtomicOperationExceptionReason.UNAVAILABLE);
			} else {
				List<CreatePair<T>> successPairs = ResultsPair.pairsWithResults(pairs);
				List<T> models = CreatePair.getObjects(successPairs);
				createResponse = new CreateResponseImpl<T>(models, errorTemplates);
			}
		} catch (AtomicOperationException e) {
			throw e;
		} catch (Exception e) {
			throw new AtomicOperationException(templates, e);
		}

		return createResponse;
	}

	@Override
	public String toString() {
		return "CreateServiceImpl [createTask=" + this.createTask + "]";
	}

}