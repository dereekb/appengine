package com.dereekb.gae.model.extension.search.document.dto;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.search.document.search.SearchableDatabaseModel;
import com.dereekb.gae.model.extension.search.document.search.dto.SearchableDatabaseModelData;
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
public class SearchableModelDataBuilder<M extends SearchableDatabaseModel, D extends SearchableDatabaseModelData> extends DatabaseModelDataBuilder<M, D> {

	public SearchableModelDataBuilder(Class<D> type) throws IllegalArgumentException {
		super(type);
	}

	public SearchableModelDataBuilder(Factory<D> factory) throws IllegalArgumentException {
		super(factory);
	}

	@Override
	public D convertSingle(M input) throws ConversionFailureException {
		D data = super.convertSingle(input);

		// Search ID
		data.setSearchId(input.getSearchIdentifier());

		return data;
	}

}
