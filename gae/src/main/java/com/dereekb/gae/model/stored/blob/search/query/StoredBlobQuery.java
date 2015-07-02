package com.dereekb.gae.model.stored.blob.search.query;

import com.dereekb.gae.model.extension.search.query.search.components.BaseModelQuery;


/**
 * Used for querying {@link StoredBlobs}.
 *
 * Can query by infoType.
 *
 * @author dereekb
 */
public class StoredBlobQuery extends BaseModelQuery {

	private boolean recent = true;

	private String infoType;

	public Boolean getRecent() {
		return this.recent;
	}

	public void setRecent(Boolean recent) {
		this.recent = recent;
	}

	public String getInfoType() {
		return this.infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

}
