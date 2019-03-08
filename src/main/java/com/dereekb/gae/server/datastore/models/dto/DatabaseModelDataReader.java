package com.dereekb.gae.server.datastore.models.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.impl.BasicFactoryImpl;

/**
 * Abstract {@link DirectionalConverter} for a {@link DatabaseModelData} to
 * {@link DatabaseModel}.
 * 
 * @author dereekb
 *
 * @param <M>
 *            model type
 * @param <D>
 *            dto type
 */
public abstract class DatabaseModelDataReader<M extends DatabaseModel, D extends DatabaseModelData> extends AbstractDirectionalConverter<D, M> {

	private Factory<M> factory;

	public DatabaseModelDataReader(Class<M> type) {
		this(new BasicFactoryImpl<M>(type));
	}

	public DatabaseModelDataReader(Factory<M> factory) {
		this.setFactory(factory);
	}

	public Factory<M> getFactory() {
		return this.factory;
	}

	public void setFactory(Factory<M> factory) throws IllegalArgumentException {
		if (factory == null) {
			throw new IllegalArgumentException("factory cannot be null.");
		}

		this.factory = factory;
	}

	// MARK: AbstractDirectionalConverter
	@Override
	public M convertSingle(D input) throws ConversionFailureException {
		M model = this.factory.make();

		// ID
		model.setModelKey(input.getModelKey());

		return model;
	}

}