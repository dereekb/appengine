package com.dereekb.gae.server.app.model.app.shared.dto;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.app.model.app.shared.impl.AbstractAppRelatedModel;
import com.dereekb.gae.server.datastore.models.dto.DatedDatabaseModelDataBuilder;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Abstract data builder for {@link AbstractAppRelatedModelData}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <D>
 *            dto type
 */
public abstract class AbstractAppRelatedModelDataBuilder<M extends AbstractAppRelatedModel<M>, D extends AbstractAppRelatedModelData> extends DatedDatabaseModelDataBuilder<M, D> {

	public AbstractAppRelatedModelDataBuilder(Class<D> type) throws IllegalArgumentException {
		super(type);
	}

	public AbstractAppRelatedModelDataBuilder(Factory<D> factory) throws IllegalArgumentException {
		super(factory);
	}

	@Override
	public D convertSingle(M input) throws ConversionFailureException {
		D data = super.convertSingle(input);

		// MARK: Info

		// MARK: Links
		data.setApp(ObjectifyKeyUtility.idFromKey(input.getApp()));

		return data;
	}

}
