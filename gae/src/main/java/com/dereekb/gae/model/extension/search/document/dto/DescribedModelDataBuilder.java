package com.dereekb.gae.model.extension.search.document.dto;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedDatabaseModel;
import com.dereekb.gae.model.extension.links.descriptor.impl.dto.DescribedDatabaseModelData;
import com.dereekb.gae.server.datastore.models.dto.DatabaseModelDataBuilder;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelData;
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
public abstract class DescribedModelDataBuilder<M extends DescribedDatabaseModel, D extends DescribedDatabaseModelData> extends SearchableModelDataBuilder<M, D> {

	public DescribedModelDataBuilder(Class<D> type) throws IllegalArgumentException {
		super(type);
	}

	public DescribedModelDataBuilder(Factory<D> factory) throws IllegalArgumentException {
		super(factory);
	}

	@Override
	public D convertSingle(M input) throws ConversionFailureException {
		D data = super.convertSingle(input);

		data.setDescriptor(input.getDescriptor());

		return data;
	}

}
