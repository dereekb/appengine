package com.dereekb.gae.server.datastore.models.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.DatedDatabaseModel;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Abstract {@link DirectionalConverter} for a {@link DatabaseModelData} to
 * {@link DatedDatabaseModel}.
 * 
 * @author dereekb
 *
 * @param <M>
 *            model type
 * @param <D>
 *            dto type
 */
public abstract class DatedDatabaseModelDataReader<M extends DatedDatabaseModel, D extends DatabaseModelData> extends DatabaseModelDataReader<M, D> {

	public DatedDatabaseModelDataReader(Class<M> type) {
		super(type);
	}

	public DatedDatabaseModelDataReader(Factory<M> factory) {
		super(factory);
	}

	// MARK: AbstractDirectionalConverter
	@Override
	public M convertSingle(D input) throws ConversionFailureException {
		M model = this.convertSingle(input);

		// ID
		model.setDate(input.getDateValue());

		return model;
	}

}