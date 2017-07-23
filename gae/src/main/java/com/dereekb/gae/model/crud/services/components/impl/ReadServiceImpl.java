package com.dereekb.gae.model.crud.services.components.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.model.crud.services.components.AtomicReadService;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.ReadRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.services.response.impl.ReadResponseImpl;
import com.dereekb.gae.model.crud.task.ReadTask;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.pairs.impl.ResultsPair;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * Default implementation of {@link ReadService} that uses a
 * {@link IterableTask} with {@link ReadPair} to perform a read.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ReadServiceImpl<T extends UniqueModel>
        implements AtomicReadService<T> {

	private ReadTask<T> readTask;

	public ReadServiceImpl(ReadTask<T> readTask) {
		this.readTask = readTask;
	}

	public ReadTask<T> getReadTask() {
		return this.readTask;
	}

	public void setReadTask(ReadTask<T> readTask) {
		this.readTask = readTask;
	}

	// MARK: AtomicReadService
	@Override
	public T read(ModelKey key) throws AtomicOperationException {
		Collection<ModelKey> keys = SingleItem.withValue(key);
		Collection<T> read = this.read(keys);
		List<T> models = new ArrayList<T>(read);
		return models.get(0);
	}

	@Override
	public Collection<T> read(Collection<ModelKey> keys) throws AtomicOperationException {
		ReadRequestOptions options = new ReadRequestOptionsImpl(true);
		ReadRequest request = new KeyReadRequest(keys, options);
		ReadResponse<T> response = this.read(request);
		return response.getModels();
	}

	// MARK: ReadService
	@Override
	public ReadResponse<T> read(ReadRequest request) throws AtomicOperationException {
		ReadResponse<T> readResponse = null;

		Iterable<ModelKey> keys = request.getModelKeys();
		ReadRequestOptions options = request.getOptions();

		// Execute Function
		List<ReadPair<T>> pairs = ReadPair.createPairsForKeys(keys);

		try {
			this.readTask.doTask(pairs);

			HashMapWithList<FilterResult, ReadPair<T>> results = ResultsPair.filterSuccessfulPairs(pairs);
			List<ReadPair<T>> errorPairs = results.valuesForKey(FilterResult.FAIL);
			List<ModelKey> errorKeys = ReadPair.getKeys(errorPairs);

			if (errorKeys.size() > 0 && options.isAtomic()) {
				throw new AtomicOperationException(errorKeys, AtomicOperationExceptionReason.UNAVAILABLE);
			} else {
				List<ReadPair<T>> successPairs = ResultsPair.pairsWithResults(pairs);
				List<T> models = ReadPair.getObjects(successPairs);
				readResponse = new ReadResponseImpl<T>(models, errorKeys);
			}
		} catch (AtomicOperationException e) {
			throw e;
		} catch (Exception e) {
			throw new AtomicOperationException(keys, e);
		}

		return readResponse;
	}

	@Override
	public String toString() {
		return "ReadServiceImpl [readTask=" + this.readTask + "]";
	}

}
