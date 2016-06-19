package com.dereekb.gae.model.crud.services.components.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.components.UpdateService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.impl.ModelReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.ReadRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.model.crud.services.response.impl.UpdateResponseImpl;
import com.dereekb.gae.model.crud.services.response.pair.UpdateResponseFailurePair;
import com.dereekb.gae.model.crud.task.UpdateTask;
import com.dereekb.gae.model.crud.task.config.UpdateTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.UpdateTaskConfigImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * Default implementation of {@link UpdateService} that uses a
 * {@link IterableTask} with {@link UpdatePair} to perform a read.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class UpdateServiceImpl<T extends UniqueModel>
        implements UpdateService<T> {

	private ReadService<T> readService;
	private UpdateTask<T> updateTask;

	public UpdateServiceImpl() {}

	public UpdateServiceImpl(ReadService<T> readService, UpdateTask<T> updateTask) {
		this.readService = readService;
		this.updateTask = updateTask;
	}

	public ReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(ReadService<T> readService) {
		this.readService = readService;
	}

	public UpdateTask<T> getUpdateTask() {
		return this.updateTask;
	}

	public void setUpdateTask(UpdateTask<T> updateTask) {
		this.updateTask = updateTask;
	}

	// MARK: UpdateService
	@Override
	public UpdateResponse<T> update(UpdateRequest<T> request) throws AtomicOperationException, IllegalArgumentException {
		UpdateResponse<T> updateResponse = null;

		UpdateRequestOptions options = request.getOptions();
		Collection<T> updateTemplates = request.getTemplates();

		// Read Models to update
		boolean atomic = options.isAtomic();
		ReadRequestOptions readOptions = new ReadRequestOptionsImpl(atomic);
		ModelReadRequest readRequest = new ModelReadRequest(updateTemplates, readOptions);

		ReadResponse<T> readResponse = this.readService.read(readRequest);
		Collection<T> models = readResponse.getModels();

		// Create Pairs
		List<UpdatePair<T>> pairs = UpdatePair.makePairs(models, updateTemplates);

		try {
			UpdateTaskConfig config = new UpdateTaskConfigImpl(atomic);
			this.updateTask.doTask(pairs, config);

			HashMapWithList<FilterResult, UpdatePair<T>> results = UpdatePair.filterSuccessfulPairs(pairs);

			List<UpdatePair<T>> successfulPairs = results.valuesForKey(FilterResult.PASS);
			List<T> updated = UpdatePair.getKeys(successfulPairs);

			List<UpdatePair<T>> errorPairs = results.valuesForKey(FilterResult.FAIL);
			List<UpdateResponseFailurePair<T>> failurePairs = UpdateResponseFailurePair.createFailurePairs(errorPairs);

			updateResponse = new UpdateResponseImpl<T>(updated, failurePairs);
		} catch (AtomicOperationException e) {
			throw e;
		} catch (Exception e) {
			throw new AtomicOperationException(updateTemplates, e);
		}

		return updateResponse;
	}

}