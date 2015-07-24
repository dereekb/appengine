package com.dereekb.gae.model.crud.services.components.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.function.UpdateFunction;
import com.dereekb.gae.model.crud.function.pairs.UpdatePair;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.components.UpdateService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.UpdateRequestOptions;
import com.dereekb.gae.model.crud.services.request.impl.ModelReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.model.crud.services.response.impl.UpdateResponseImpl;
import com.dereekb.gae.model.crud.services.response.pair.UpdateResponseFailurePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * Default implementation of {@link UpdateService} using a {@link Factory} for
 * {@link UpdateFunction}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class UpdateServiceImpl<T extends UniqueModel>
        implements UpdateService<T> {

	private final ReadService<T> readService;
	private final Factory<UpdateFunction<T>> factory;

	public UpdateServiceImpl(ReadService<T> readService, Factory<UpdateFunction<T>> factory) {
		this.readService = readService;
		this.factory = factory;
    }

	public ReadService<T> getReadService() {
		return this.readService;
	}

	public Factory<UpdateFunction<T>> getFactory() {
		return this.factory;
	}

	@Override
	public UpdateResponse<T> update(UpdateRequest<T> request) throws AtomicOperationException {
		UpdateResponse<T> updateResponse = null;
		UpdateRequestOptions options = request.getOptions();

		Collection<T> updateTemplates = request.getTemplates();

		//Read Models to update
		ReadRequestOptions readOptions = new ReadRequestOptions(true);
		ModelReadRequest<T> readRequest = new ModelReadRequest<T>(updateTemplates, readOptions);

		ReadResponse<T> readResponse = this.readService.read(readRequest);
		Collection<T> models = readResponse.getModels();

		// Create Pairs
		List<UpdatePair<T>> pairs = UpdatePair.makePairs(models, updateTemplates);

		UpdateFunction<T> function = this.factory.make();
		function.setAtomic(options.isAtomic());
		function.addObjects(pairs);

		try {
			function.run();

			HashMapWithList<FilterResult, UpdatePair<T>> results = UpdatePair.filterSuccessfulPairs(pairs);

			List<UpdatePair<T>> successfulPairs = results.getElements(FilterResult.PASS);
			List<T> updated = UpdatePair.getKeys(successfulPairs);

			List<UpdatePair<T>> errorPairs = results.getElements(FilterResult.FAIL);
			List<UpdateResponseFailurePair<T>> failurePairs = UpdateResponseFailurePair
			        .createFailurePairs(errorPairs);

			updateResponse = new UpdateResponseImpl<T>(updated, failurePairs);
		} catch (AtomicOperationException e) {
			throw e;
		} catch (Exception e) {
			throw new AtomicOperationException(updateTemplates, e);
		}

		return updateResponse;
	}

}