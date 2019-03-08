package com.dereekb.gae.server.datastore.models.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.impl.BasicFactoryImpl;

/**
 * Abstract {@link DirectionalConverter} for a {@link DatabaseModel} to
 * {@link DatabaseModelData}.
 * 
 * @author dereekb
 *
 * @param <M>
 *            model type
 * @param <D>
 *            dto type
 */
public abstract class DatabaseModelDataBuilder<M extends DatabaseModel, D extends DatabaseModelData> extends AbstractDirectionalConverter<M, D> {

	private Factory<D> factory;

	public DatabaseModelDataBuilder(Class<D> type) throws IllegalArgumentException {
		this(new BasicFactoryImpl<D>(type));
	}

	public DatabaseModelDataBuilder(Factory<D> factory) throws IllegalArgumentException {
		this.setFactory(factory);
	}

	public Factory<D> getFactory() {
		return this.factory;
	}

	public void setFactory(Factory<D> factory) throws IllegalArgumentException {
		if (factory == null) {
			throw new IllegalArgumentException("factory cannot be null.");
		}

		this.factory = factory;
	}

	// MARK: AbstractDirectionalConverter
	@Override
	public D convertSingle(M input) throws ConversionFailureException {
		D data = this.factory.make();

		// ID
		data.setModelKey(input.getModelKey());

		return data;
	}

}
