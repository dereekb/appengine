package com.dereekb.gae.server.datastore.models.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.owner.OwnedDatabaseModel;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Abstract {@link DirectionalConverter} for a {@link OwnedDatabaseModelData} to
 * {@link OwnedDatabaseModel}.
 * 
 * @author dereekb
 *
 * @param <M>
 *            model type
 * @param <D>
 *            dto type
 */
public abstract class OwnedDatabaseModelDataReader<M extends OwnedDatabaseModel, D extends OwnedDatabaseModelData> extends DatabaseModelDataReader<M, D> {

	public OwnedDatabaseModelDataReader(Class<M> type) {
		super(type);
	}

	public OwnedDatabaseModelDataReader(Factory<M> factory) {
		super(factory);
	}

	@Override
	public M convertSingle(D input) throws ConversionFailureException {
		M model = super.convertSingle(input);

		// Owner ID
		model.setOwnerId(input.getOwnerId());

		return model;
	}

}