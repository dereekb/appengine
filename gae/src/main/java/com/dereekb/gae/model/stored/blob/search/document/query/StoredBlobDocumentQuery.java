package com.dereekb.gae.model.stored.blob.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.deprecated.components.AbstractDocumentSearchQuery;
import com.dereekb.gae.model.stored.blob.StoredBlob;

/**
 * Document search query for {@link StoredBlob}.
 *
 * @author dereekb
 */
public final class StoredBlobDocumentQuery extends AbstractDocumentSearchQuery {

	private String infoType;

	private String infoId;

	public StoredBlobDocumentQuery() {}

	public StoredBlobDocumentQuery(String infoType, String infoId) {
		this.infoType = infoType;
		this.infoId = infoId;
	}

	public String getInfoType() {
		return this.infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

}
