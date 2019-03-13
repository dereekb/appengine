package com.dereekb.gae.model.crud.services.response.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.response.ModelServiceResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.pairs.ResultsPairState;
import com.dereekb.gae.utilities.collections.pairs.impl.ResultsPair;

/**
 * {@link ServiceResponseImpl} extension for
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelServiceResponseImpl<T extends UniqueModel> extends ServiceResponseImpl
        implements ModelServiceResponse<T> {

	private Collection<T> models;

	public ModelServiceResponseImpl() {
		this(new ArrayList<T>());
	}

	public ModelServiceResponseImpl(Collection<T> models) {
		super();
		this.setModels(models);
	}

	public ModelServiceResponseImpl(Collection<T> models, Collection<ModelKey> unavailable) {
		super(unavailable);
		this.setModels(models);
	}

	public ModelServiceResponseImpl(Collection<T> models,
	        Collection<ModelKey> filtered,
	        Collection<ModelKey> unavailable) {
		super(filtered, unavailable);
		this.setModels(models);
	}

	public ModelServiceResponseImpl(ModelServiceResponse<T> response) {
		super(response);
		this.setModels(response.getModels());
	}

	@Override
	public Collection<T> getModels() {
		return this.models;
	}

	public void setModels(Collection<T> models) throws IllegalArgumentException {
		if (models == null) {
			throw new IllegalArgumentException("Models cannot be null.");
		}

		this.models = models;
	}

	// MARK: Utility
	/**
	 * Generates a new {@link ServiceResponseImpl} using a set of
	 * {@link ResultsPair} values.
	 *
	 * The pairs are iterated over and are put into three lists used to
	 * initialize the {@link ServiceResponseImpl}.
	 *
	 * @param pairs
	 * @return
	 */
	public static <T extends UniqueModel, P extends ResultsPair<ModelKey, T>> ModelServiceResponseImpl<T> responseForPairs(Iterable<P> pairs) {
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

		ModelServiceResponseImpl<T> response = new ModelServiceResponseImpl<T>(models, filtered, unavailable);
		return response;
	}

}
