package com.dereekb.gae.server.app.model.app.shared.dto;

import com.dereekb.gae.server.app.model.app.shared.impl.AbstractAppRelatedModel;
import com.dereekb.gae.server.datastore.models.dto.DatabaseModelData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO for an {@link AbstractAppRelatedModel}.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractAppRelatedModelData extends DatabaseModelData {

	private static final long serialVersionUID = 1L;

	protected Long app;

	public Long getApp() {
		return this.app;
	}

	public void setApp(Long app) {
		this.app = app;
	}

}
