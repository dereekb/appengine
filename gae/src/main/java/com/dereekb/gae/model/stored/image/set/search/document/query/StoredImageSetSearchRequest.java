package com.dereekb.gae.model.stored.image.set.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequest;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;

/**
 * Search request for a {@link StoredImageSet}.
 *
 * @author dereekb
 *
 */
public class StoredImageSetSearchRequest extends AbstractModelDocumentRequest {

	private String label;
	private String detail;
	private String tags;

	public StoredImageSetSearchRequest() {}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
