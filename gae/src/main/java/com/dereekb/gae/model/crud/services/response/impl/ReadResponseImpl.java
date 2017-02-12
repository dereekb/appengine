package com.dereekb.gae.model.crud.services.response.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.ModelServiceResponse;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ReadResponse} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ReadResponseImpl<T extends UniqueModel> extends ModelServiceResponseImpl<T>
        implements ReadResponse<T> {

	public ReadResponseImpl() {
		super();
	}

	public ReadResponseImpl(Collection<T> models, Collection<ModelKey> filtered, Collection<ModelKey> unavailable) {
		super(models, filtered, unavailable);
	}

	public ReadResponseImpl(Collection<T> models, Collection<ModelKey> unavailable) {
		super(models, unavailable);
	}

	public ReadResponseImpl(Collection<T> models) {
		super(models);
	}

	public ReadResponseImpl(ModelServiceResponse<T> response) {
		super(response);
	}

	/**
	 * Convenience constructor for building unavailable models.
	 *
	 * @param unavailable
	 * @return
	 */
	public static <T extends UniqueModel> ReadResponseImpl<T> unavailable(Collection<ModelKey> unavailable) {
		return new ReadResponseImpl<T>(new ArrayList<T>(), unavailable);
	}

}
