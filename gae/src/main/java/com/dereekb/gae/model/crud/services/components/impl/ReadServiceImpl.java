package com.dereekb.gae.model.crud.services.components.impl;

import java.util.List;

import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.services.response.impl.ReadResponseImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
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
        implements ReadService<T> {

	private IterableTask<ReadPair<T>> readTask;

	public ReadServiceImpl(IterableTask<ReadPair<T>> readTask) {
		this.readTask = readTask;
	}

	public IterableTask<ReadPair<T>> getReadTask() {
		return this.readTask;
	}

	public void setReadTask(IterableTask<ReadPair<T>> readTask) {
		this.readTask = readTask;
	}

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
			List<ReadPair<T>> errorPairs = results.getElements(FilterResult.FAIL);
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

}
