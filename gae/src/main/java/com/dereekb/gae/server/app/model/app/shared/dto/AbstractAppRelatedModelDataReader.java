package com.dereekb.gae.server.app.model.app.shared.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.shared.impl.AbstractAppRelatedModel;
import com.dereekb.gae.server.datastore.models.dto.DatedDatabaseModelDataReader;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link AbstractAppRelatedModelData}
 * instance to {@link AbstractAppRelatedModel}.
 *
 * @author dereekb
 *
 * @param <M>
 *            model type
 * @param <D>
 *            dto type
 */
public abstract class AbstractAppRelatedModelDataReader<M extends AbstractAppRelatedModel<M>, D extends AbstractAppRelatedModelData> extends DatedDatabaseModelDataReader<M, D> {

	private static final ObjectifyKeyUtility<App> APP_KEY_UTIL = ObjectifyKeyUtility
	        .make(App.class);

	public AbstractAppRelatedModelDataReader(Class<M> type) {
		super(type);
	}

	public AbstractAppRelatedModelDataReader(Factory<M> factory) {
		super(factory);
	}

	@Override
	public M convertSingle(D input) throws ConversionFailureException {
		M model = super.convertSingle(input);

		// MARK: Info

		// MARK: Links
		model.setApp(APP_KEY_UTIL.keyFromId(input.getApp()));

		return model;
	}

}
