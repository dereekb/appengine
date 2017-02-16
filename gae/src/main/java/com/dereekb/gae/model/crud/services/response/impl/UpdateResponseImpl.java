package com.dereekb.gae.model.crud.services.response.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.model.crud.services.response.pair.InvalidTemplatePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link UpdateResponse} implementation
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class UpdateResponseImpl<T extends UniqueModel> extends ModelServiceResponseImpl<T>
        implements UpdateResponse<T> {

	private Collection<InvalidTemplatePair<T>> failurePairs;

	public UpdateResponseImpl(Collection<T> models, Collection<InvalidTemplatePair<T>> failurePairs) {
		super(models);
		this.setFailurePairs(failurePairs);
	}

	public UpdateResponseImpl(Collection<T> models,
	        Collection<ModelKey> unavailable,
	        Collection<InvalidTemplatePair<T>> failurePairs) {
		super(models, unavailable);
		this.setFailurePairs(failurePairs);
	}

	public UpdateResponseImpl(Collection<T> models,
	        Collection<ModelKey> filtered,
	        Collection<ModelKey> unavailable,
	        Collection<InvalidTemplatePair<T>> failurePairs) {
		super(models, filtered, unavailable);
		this.setFailurePairs(failurePairs);
	}

	// MARK: UpdateResponse
	@Override
	public Collection<InvalidTemplatePair<T>> getUpdateFailures() {
		return this.failurePairs;
	}

	public void setFailurePairs(Collection<InvalidTemplatePair<T>> failurePairs) {
		if (failurePairs == null) {
			throw new IllegalArgumentException("failurePairs cannot be null.");
		}

		this.failurePairs = failurePairs;
	}

	// MARK: Override
	@Override
	public List<ModelKey> getFailed() {
		Collection<InvalidTemplatePair<T>> failedPairs = this.getUpdateFailures();

		List<ModelKey> failedPairsKeys = ModelKey.readModelKeysFromKeyed(failedPairs);
		List<ModelKey> filteredAndUnavailable = super.getFailed();

		failedPairsKeys.addAll(filteredAndUnavailable);

		return failedPairsKeys;
	}

	@Override
	public Collection<ModelKey> getMissingKeys() {
		return super.getFailed();
	}

}
