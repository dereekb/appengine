package com.dereekb.gae.model.crud.services.components.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.options.CreateRequestOptions;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.impl.CreateResponseImpl;
import com.dereekb.gae.model.crud.services.response.pair.InvalidCreateTemplatePair;
import com.dereekb.gae.model.crud.task.CreateTask;
import com.dereekb.gae.model.crud.task.config.CreateTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.CreateTaskConfigImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.pairs.impl.ResultsPair;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;

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

	private CreateTask<T> createTask;

	public CreateServiceImpl(CreateTask<T> createTask) {
		this.createTask = createTask;
	}

	public CreateTask<T> getCreateTask() {
		return this.createTask;
	}

	public void setCreateTask(CreateTask<T> createTask) {
		this.createTask = createTask;
	}

	// MARK: CreateService
	@Override
	public CreateResponse<T> create(CreateRequest<T> request) throws AtomicOperationException {
		CreateResponse<T> createResponse = null;

		Collection<T> templates = request.getTemplates();
		CreateRequestOptions options = request.getOptions();

		List<CreatePair<T>> pairs = CreatePair.createPairsForModels(templates);

		try {
			CreateTaskConfig config = new CreateTaskConfigImpl(options.isAtomic());
			boolean atomicOperationFailure = false;

			try {
				this.createTask.doTask(pairs, config);
			} catch (AtomicOperationException e) {
				// Task was not completed successfully.
				atomicOperationFailure = true;
			}

			List<InvalidCreateTemplatePair<T>> failurePairs = InvalidCreateTemplatePair.makeWithCreatePairs(pairs);

			// Build Response
			if (atomicOperationFailure) {
				MultiKeyedInvalidAttributeException exception = new MultiKeyedInvalidAttributeException(failurePairs);
				throw new AtomicOperationException(exception);
			} else {
				HashMapWithList<FilterResult, CreatePair<T>> results = ResultsPair.filterSuccessfulPairs(pairs);

				List<CreatePair<T>> successPairs = results.valuesForKey(FilterResult.PASS);
				List<T> models = CreatePair.getObjects(successPairs);

				createResponse = new CreateResponseImpl<T>(models, failurePairs);
			}
		} catch (AtomicOperationException e) {
			throw e;	// Pass Atomic Operation Through
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