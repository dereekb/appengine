package com.dereekb.gae.model.crud.services.components.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.DeletePair;
import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.request.options.DeleteRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.ReadRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.services.response.impl.DeleteResponseImpl;
import com.dereekb.gae.model.crud.task.DeleteTask;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * Default implementation of {@link DeleteService} using a {@link IterableTask}
 * with {@link DeletePair} to delete models.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class DeleteServiceImpl<T extends UniqueModel>
        implements DeleteService<T> {

	private ReadService<T> readService;
	private DeleteTask<T> deleteTask;

	public DeleteServiceImpl() {}

	public DeleteServiceImpl(ReadService<T> readService, DeleteTask<T> deleteTask) {
		this.readService = readService;
		this.deleteTask = deleteTask;
	}

	public ReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(ReadService<T> readService) {
		this.readService = readService;
	}

	public DeleteTask<T> getDeleteTask() {
		return this.deleteTask;
	}

	public void setDeleteTask(DeleteTask<T> deleteTask) {
		this.deleteTask = deleteTask;
	}

	@Override
	public DeleteResponse<T> delete(DeleteRequest<T> request) throws AtomicOperationException {
		DeleteResponse<T> deleteResponse = null;

		Collection<ModelKey> deleteKeys = request.getTargetKeys();
		DeleteRequestOptions options = request.getOptions();

		// Read Models to delete. Missing models are ignored.
		ReadRequestOptions readOptions = new ReadRequestOptionsImpl(false);
		KeyReadRequest readRequest = new KeyReadRequest(deleteKeys, readOptions);

		ReadResponse<T> readResponse = this.readService.read(readRequest);
		Collection<T> models = readResponse.getModels();
		Collection<ModelKey> filtered = readResponse.getFiltered();
		Collection<ModelKey> unavailable = readResponse.getUnavailable();

		if (filtered.isEmpty() == false && options.isAtomic()) {
			throw new AtomicOperationException(filtered, AtomicOperationExceptionReason.UNAVAILABLE);
		}

		List<DeletePair<T>> pairs = DeletePair.deletePairsForModels(models);

		try {
			this.deleteTask.doTask(pairs);
			/*
			 * Once the function runs, no exceptions should occur after the
			 * point the delete delegate is called, otherwise there is a chance
			 * an inconsistency can occur.
			 *
			 * Some pairs still may be filtered out internally as part of the
			 * function before being deleted.
			 */

			HashMapWithList<FilterResult, DeletePair<T>> results = ResultsPair.filterSuccessfulPairs(pairs);
			List<DeletePair<T>> deletedPairs = results.valuesForKey(FilterResult.PASS);
			List<T> deletedModels = DeletePair.getKeys(deletedPairs);

			List<DeletePair<T>> filteredPairs = results.valuesForKey(FilterResult.FAIL);
			List<T> filteredModels = DeletePair.getKeys(filteredPairs);
			List<ModelKey> filteredKeys = ModelKey.readModelKeys(filteredModels);

			deleteResponse = new DeleteResponseImpl<T>(deletedModels, filteredKeys, unavailable);
		} catch (Exception e) {
			List<ModelKey> missing = ModelKey.readModelKeys(models);
			throw new AtomicOperationException(missing, e);
		}

		return deleteResponse;
	}

	@Override
	public String toString() {
		return "DeleteServiceImpl [readService=" + this.readService + ", deleteTask=" + this.deleteTask + "]";
	}

}