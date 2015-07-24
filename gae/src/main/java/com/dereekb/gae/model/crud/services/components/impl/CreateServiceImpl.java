package com.dereekb.gae.model.crud.services.components.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.function.CreateFunction;
import com.dereekb.gae.model.crud.function.pairs.CreatePair;
import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.CreateRequestOptions;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.impl.CreateResponseImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * Default implementation of {@link CreateService} using a {@link Factory} for
 * {@link CreateFunction}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class CreateServiceImpl<T extends UniqueModel>
        implements CreateService<T> {

	private final Factory<CreateFunction<T>> factory;

	public CreateServiceImpl(Factory<CreateFunction<T>> factory) {
		this.factory = factory;
	}

	@Override
	public CreateResponse<T> create(CreateRequest<T> request) throws AtomicOperationException {
		CreateResponse<T> createResponse = null;

		Collection<T> templates = request.getTemplates();
		CreateRequestOptions options = request.getOptions();

		List<CreatePair<T>> pairs = CreatePair.createPairsForModels(templates);
		CreateFunction<T> function = this.factory.make();
		function.addObjects(pairs);

		try {
			function.run();

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

}