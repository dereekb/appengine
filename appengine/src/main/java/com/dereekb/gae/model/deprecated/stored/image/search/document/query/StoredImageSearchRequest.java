package com.dereekb.gae.model.stored.image.search.document.query;

import com.dereekb.gae.model.deprecated.stored.image.StoredImage;
import com.dereekb.gae.model.deprecated.stored.image.search.document.query.StoredImageSearchBuilder.StoredImageSearch;
import com.dereekb.gae.model.extension.deprecated.search.document.search.service.model.impl.AbstractModelDocumentRequest;

/**
 * Search request for a {@link StoredImage}.
 *
 * @author dereekb
 *
 */
public class StoredImageSearchRequest extends AbstractModelDocumentRequest {

	public static final StoredImageSearchBuilder BUILDER = new StoredImageSearchBuilder("");

	private StoredImageSearch search;

	public StoredImageSearchRequest() {
		this.search = BUILDER.make();
	}

	public StoredImageSearch getSearch() {
		return this.search;
	}

	public void setSearch(StoredImageSearch search) throws IllegalArgumentException {
		if (search == null) {
			throw new IllegalArgumentException("Search cannot be null.");
		}

		this.search = search;
	}

	@Override
	public String toString() {
		return "StoredImageSearchRequest [search=" + this.search + "]";
	}

}
