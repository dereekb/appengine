package com.dereekb.gae.model.crud.services.components.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.model.crud.services.components.AtomicReadService;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.ReadRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.services.response.impl.ExistsReadResponse;
import com.dereekb.gae.model.crud.services.response.impl.ReadResponseImpl;
import com.dereekb.gae.model.crud.task.ReadTask;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.pairs.impl.ResultPairImpl;
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
	public ReadResponse<ModelKey> exists(ReadRequest request) throws AtomicOperationException {
		ReadResponse<T> readResponse = this.read(request);
		return new ExistsReadResponse(readResponse);
	}

	@Override
	public ReadResponse<T> read(ReadRequest request) throws AtomicOperationException {
		ReadResponse<T> readResponse = null;

		Iterable<ModelKey> keys = request.getModelKeys();
		ReadRequestOptions options = request.getOptions();
		boolean atomic = options.isAtomic();

		try {
			readResponse = doReadForModels(this.readTask, keys, atomic);
		} catch (AtomicOperationException e) {
			throw e;
		} catch (Exception e) {
			throw new AtomicOperationException(keys, e);
		}

		return readResponse;
	}

	/**
	 * Static function that performs a read using a {@link ReadTask} and returns
	 * a {@link ReadResponse}.
	 * 
	 * @param readTask
	 *            {@link ReadTask}. Never {@code null}.
	 * @param keys
	 *            {@link Iterable}. Never {@code null}.
	 * @param atomic
	 *            if atomic request or not.
	 * @return {@link ReadResponse}. Never {@code null}.
	 * @throws AtomicOperationException
	 *             thrown if the atomic request fails.
	 */
	public static <T extends UniqueModel> ReadResponse<T> doReadForModels(ReadTask<T> readTask,
	                                                                      Iterable<ModelKey> keys,
	                                                                      boolean atomic)
	        throws AtomicOperationException {
		List<ReadPair<T>> pairs = ReadPair.createPairsForKeys(keys);
		readTask.doReadTask(pairs, atomic);

		List<T> models = null;
		List<ModelKey> errorKeys = null;

		if (atomic) {
			// No Error Keys or else an exception would have been thrown.
			errorKeys = Collections.emptyList();
			models = ReadPair.getObjects(pairs);
		} else {
			HashMapWithList<FilterResult, ReadPair<T>> results = ResultPairImpl.filterSuccessfulPairs(pairs);

			List<ReadPair<T>> errorPairs = results.valuesForKey(FilterResult.FAIL);
			errorKeys = ReadPair.getKeys(errorPairs);

			List<ReadPair<T>> successPairs = results.valuesForKey(FilterResult.PASS);
			models = ReadPair.getObjects(successPairs);
		}

		return new ReadResponseImpl<T>(models, errorKeys);
	}

	@Override
	public String toString() {
		return "ReadServiceImpl [readTask=" + this.readTask + "]";
	}

}
