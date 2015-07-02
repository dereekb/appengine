package com.dereekb.gae.model.crud.services.response.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
import com.dereekb.gae.utilities.collections.pairs.ResultsPairState;

public class ServiceResponse<T> {

	protected Collection<T> models;
	protected Collection<ModelKey> filtered;
	protected Collection<ModelKey> unavailable;

	public ServiceResponse(ServiceResponse<T> response) {
		this(response.models, response.filtered, response.unavailable);
	}

	public ServiceResponse(Collection<T> models) {
		this(models, null, null);
	}

	public ServiceResponse(Collection<T> models, Collection<ModelKey> unavailable) {
		this(models, null, unavailable);
	}

	public ServiceResponse(Collection<T> models, Collection<ModelKey> filtered, Collection<ModelKey> unavailable) {
		this.setModels(models);
		this.setFiltered(filtered);
		this.setUnavailable(unavailable);
	}

	protected void setModels(Collection<T> models) {
		if (models == null) {
			models = Collections.emptyList();
		}

		this.models = models;
	}

	protected void setFiltered(Collection<ModelKey> filtered) {
		if (filtered == null) {
			filtered = Collections.emptyList();
		}

		this.filtered = filtered;
	}

	protected void setUnavailable(Collection<ModelKey> unavailable) {
		if (unavailable == null) {
			unavailable = Collections.emptyList();
		}

		this.unavailable = unavailable;
	}

	public List<ModelKey> getAllErrorKeys() {
		List<ModelKey> errors = new ArrayList<ModelKey>();

		errors.addAll(this.filtered);
		errors.addAll(this.unavailable);

		return errors;
	}

	public boolean isComplete() {
		return this.unavailable.isEmpty() && this.filtered.isEmpty();
	}

	/**
	 * Generates a new {@link ServiceResponse} using a set of
	 * {@link ResultsPair} values.
	 *
	 * The pairs are iterated over and are put into three lists used to
	 * initialize the {@link ServiceResponse}.
	 *
	 * @param pairs
	 * @return
	 */
	public static <T, P extends ResultsPair<ModelKey, T>> ServiceResponse<T> responseForPairs(Iterable<P> pairs) {
		List<T> models = new ArrayList<T>();
		List<ModelKey> filtered = new ArrayList<ModelKey>();
		List<ModelKey> unavailable = new ArrayList<ModelKey>();

		for (P pair : pairs) {
			ResultsPairState state = pair.getState();

			switch (state) {
				case REPLACED:
				case SUCCESS:
					models.add(pair.getResult());
					break;
				case CLEARED:
					filtered.add(pair.getSource());
					break;
				case NEW:
				case FAILURE:
					unavailable.add(pair.getSource());
					break;
			}
		}

		ServiceResponse<T> response = new ServiceResponse<T>(models, filtered, unavailable);
		return response;
	}
}
