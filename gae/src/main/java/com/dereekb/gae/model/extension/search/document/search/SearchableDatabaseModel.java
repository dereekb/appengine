package com.dereekb.gae.model.extension.search.document.search;

import com.dereekb.gae.model.extension.search.document.SearchableUniqueModel;
import com.dereekb.gae.server.datastore.models.DatabaseModel;

/**
 * Extension of {@link DatabaseModel} that implements the
 * {@link SearchableUniqueModel} interface.
 *
 * @author dereekb
 *
 */
public abstract class SearchableDatabaseModel extends DatabaseModel
        implements SearchableUniqueModel {

	private static final long serialVersionUID = 1L;

	protected String searchIdentifier;

	@Override
	public String getSearchIdentifier() {
		return this.searchIdentifier;
	}

	@Override
	public void setSearchIdentifier(String identifier) {
		this.searchIdentifier = identifier;
	}

}
