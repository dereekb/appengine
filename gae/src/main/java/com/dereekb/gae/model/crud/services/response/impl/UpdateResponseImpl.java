package com.dereekb.gae.model.crud.services.response.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.model.crud.services.response.pair.UpdateResponseFailurePair;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link UpdateResponse} implementation
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class UpdateResponseImpl<T>
        implements UpdateResponse<T> {

	private Collection<T> updatedModels;
	private Collection<ModelKey> unavailable;
	private Collection<UpdateResponseFailurePair<T>> failed;

	public UpdateResponseImpl(Collection<T> updated,
	        Collection<ModelKey> unavailable,
	        Collection<UpdateResponseFailurePair<T>> failed) {
		this.setUpdatedModels(updated);
		this.setUnavailable(unavailable);
		this.setFailed(failed);
	}

	@Override
	public Collection<T> getUpdatedModels() {
		return this.updatedModels;
	}

	public void setUpdatedModels(Collection<T> updatedModels) {
		if (updatedModels == null) {
			throw new IllegalArgumentException("updatedModels cannot be null.");
		}

		this.updatedModels = updatedModels;
	}

	@Override
	public Collection<ModelKey> getUnavailable() {
		return this.unavailable;
	}

	public void setUnavailable(Collection<ModelKey> unavailable) {
		if (unavailable == null) {
			throw new IllegalArgumentException("unavailable cannot be null.");
		}

		this.unavailable = unavailable;
	}

	@Override
	public Collection<UpdateResponseFailurePair<T>> getFailed() {
		return this.failed;
	}

	public void setFailed(Collection<UpdateResponseFailurePair<T>> failed) {
		if (failed == null) {
			throw new IllegalArgumentException("failed cannot be null.");
		}

		this.failed = failed;
	}

	@Override
	public String toString() {
		return "UpdateResponseImpl [updatedModels=" + this.updatedModels + ", unavailable=" + this.unavailable
		        + ", failed=" + this.failed + "]";
	}

}
