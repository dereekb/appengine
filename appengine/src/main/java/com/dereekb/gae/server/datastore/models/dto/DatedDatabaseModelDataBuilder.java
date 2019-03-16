package com.dereekb.gae.server.datastore.models.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.DatedDatabaseModel;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Abstract {@link DirectionalConverter} for a {@link DatedDatabaseModel} to
 * {@link DatabaseModelData}.
 * 
 * @author dereekb
 *
 * @param <M>
 *            model type
 * @param <D>
 *            dto type
 */
public abstract class DatedDatabaseModelDataBuilder<M extends DatedDatabaseModel, D extends DatabaseModelData> extends DatabaseModelDataBuilder<M, D> {

	public DatedDatabaseModelDataBuilder(Class<D> type) throws IllegalArgumentException {
		super(type);
	}

	public DatedDatabaseModelDataBuilder(Factory<D> factory) throws IllegalArgumentException {
		super(factory);
	}

	// MARK: AbstractDirectionalConverter
	@Override
	public D convertSingle(M input) throws ConversionFailureException {
		D data = super.convertSingle(input);

		// ID
		data.setDateValue(input.getDate());

		return data;
	}

}
