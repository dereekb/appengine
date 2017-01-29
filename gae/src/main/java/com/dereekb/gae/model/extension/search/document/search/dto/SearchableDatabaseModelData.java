package com.dereekb.gae.model.extension.search.document.search.dto;

import com.dereekb.gae.model.extension.search.document.search.SearchableDatabaseModel;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Abstract Data Transfer Object class for {@link SearchableDatabaseModel}
 * instances.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SearchableDatabaseModelData extends OwnedDatabaseModelData {

	private static final long serialVersionUID = 1L;

	protected String searchIdentifier;

	public String getSearchIdentifier() {
		return this.searchIdentifier;
	}

	public void setSearchIdentifier(String searchIdentifier) {
		this.searchIdentifier = searchIdentifier;
	}

}
