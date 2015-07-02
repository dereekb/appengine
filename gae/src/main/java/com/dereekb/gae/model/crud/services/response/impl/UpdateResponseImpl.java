package com.dereekb.gae.model.crud.services.response.impl;

import java.util.Collection;
import java.util.Collections;

import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.model.crud.services.response.pair.UpdateResponseFailurePair;

/**
 * Default implementation of {@link UpdateResponse}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class UpdateResponseImpl<T>
        implements UpdateResponse<T> {

	private final Collection<T> updated;
	private final Collection<UpdateResponseFailurePair<T>> failed;

	public UpdateResponseImpl(Collection<T> updated, Collection<UpdateResponseFailurePair<T>> failed) {
		if (failed == null) {
			failed = Collections.emptyList();
		}

		this.updated = updated;
		this.failed = failed;
	}

	@Override
	public Collection<T> getUpdatedModels() {
		return this.updated;
	}

	@Override
	public Collection<UpdateResponseFailurePair<T>> getFailed() {
		return this.failed;
	}

}
