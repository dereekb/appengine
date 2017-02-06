package com.dereekb.gae.model.extension.search.document.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.search.document.search.SearchableDatabaseModel;
import com.dereekb.gae.model.extension.search.document.search.dto.SearchableDatabaseModelData;
import com.dereekb.gae.server.datastore.models.dto.DatabaseModelDataReader;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Abstract {@link DirectionalConverter} for a
 * {@link SearchableDatabaseModelData} to {@link SearchableDatabaseModel}.
 * 
 * @author dereekb
 *
 * @param <M>
 *            model type
 * @param <D>
 *            dto type
 */
public class SearchableModelDataReader<M extends SearchableDatabaseModel, D extends SearchableDatabaseModelData> extends DatabaseModelDataReader<M, D> {

	public SearchableModelDataReader(Class<M> type) {
		super(type);
	}

	public SearchableModelDataReader(Factory<M> factory) {
		super(factory);
	}

	@Override
	public M convertSingle(D input) throws ConversionFailureException {
		M model = super.convertSingle(input);

		// Owner ID
		model.setSearchIdentifier(input.getSearchIdentifier());

		return model;
	}

}