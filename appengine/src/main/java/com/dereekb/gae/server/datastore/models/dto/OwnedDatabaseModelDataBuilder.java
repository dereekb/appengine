package com.dereekb.gae.server.datastore.models.dto;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.owner.OwnedDatabaseModel;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DatabaseModelDataBuilder} extension for
 * {@link OwnedDatabaseModelData}.
 * 
 * @author dereekb
 *
 * @param <M>
 *            model type
 * @param <D>
 *            dto type
 */
public abstract class OwnedDatabaseModelDataBuilder<M extends OwnedDatabaseModel, D extends OwnedDatabaseModelData> extends DatabaseModelDataBuilder<M, D> {

	public OwnedDatabaseModelDataBuilder(Class<D> type) {
		super(type);
	}

	public OwnedDatabaseModelDataBuilder(Factory<D> factory) {
		super(factory);
	}

	@Override
	public D convertSingle(M input) throws ConversionFailureException {
		D data = super.convertSingle(input);

		// Owner ID
		data.setOwnerId(data.getOwnerId());

		return data;
	}

}
