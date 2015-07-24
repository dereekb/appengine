package com.dereekb.gae.model.crud.services.components.impl;

import java.util.List;

import com.dereekb.gae.model.crud.function.ReadFunction;
import com.dereekb.gae.model.crud.function.pairs.ReadPair;
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
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * Default implementation of {@link ReadService} using a {@link Factory} for
 * {@link ReadFunction}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class ReadServiceImpl<T extends UniqueModel>
        implements ReadService<T> {

	private final Factory<ReadFunction<T>> factory;

	public ReadServiceImpl(Factory<ReadFunction<T>> factory) {
		this.factory = factory;
	}

	@Override
	public ReadResponse<T> read(ReadRequest<T> request) throws AtomicOperationException {
		ReadResponse<T> readResponse = null;

		Iterable<ModelKey> keys = request.getModelKeys();
		ReadRequestOptions options = request.getOptions();

		// Execute Function
		List<ReadPair<T>> pairs = ReadPair.createPairsForKeys(keys);
		ReadFunction<T> function = this.factory.make();
		function.addObjects(pairs);

		try {
			function.run();

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
