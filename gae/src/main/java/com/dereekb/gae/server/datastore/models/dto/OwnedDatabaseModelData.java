package com.dereekb.gae.server.datastore.models.dto;

import com.dereekb.gae.server.datastore.models.owner.MutableOwnedModel;
import com.dereekb.gae.server.datastore.models.owner.OwnedDatabaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Base Data Transfer Model (DTO) for a {@link OwnedDatabaseModel}.
 * 
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class OwnedDatabaseModelData extends DatabaseModelData
        implements MutableOwnedModel {

	private static final long serialVersionUID = 1L;

	protected String ownerId;

	// MARK: OwnedModel
	public String getOwner() {
		return this.ownerId;
	}

	@Override
	public String getOwnerId() {
		return this.ownerId;
	}

	@Override
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

}
