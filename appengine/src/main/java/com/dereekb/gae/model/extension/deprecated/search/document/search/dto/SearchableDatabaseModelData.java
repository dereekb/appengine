package com.dereekb.gae.model.extension.search.document.search.dto;

import com.dereekb.gae.model.extension.deprecated.search.document.search.SearchableDatabaseModel;
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
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SearchableDatabaseModelData extends OwnedDatabaseModelData {

	private static final long serialVersionUID = 1L;

	protected String searchId = null;

	public String getSearchId() {
		return this.searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

}
