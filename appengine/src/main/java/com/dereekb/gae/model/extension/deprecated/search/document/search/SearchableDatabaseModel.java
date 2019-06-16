package com.dereekb.gae.model.extension.search.document.search;

import com.dereekb.gae.model.extension.deprecated.search.document.SearchableUniqueModel;
import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.server.datastore.models.owner.OwnedDatabaseModel;

/**
 * Extension of {@link DatabaseModel} that implements the
 * {@link SearchableUniqueModel} interface.
 *
 * @author dereekb
 *
 */
public abstract class SearchableDatabaseModel extends OwnedDatabaseModel
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
