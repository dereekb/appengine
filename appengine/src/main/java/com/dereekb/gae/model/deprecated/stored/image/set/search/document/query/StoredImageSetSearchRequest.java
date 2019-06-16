package com.dereekb.gae.model.stored.image.set.search.document.query;

import com.dereekb.gae.model.deprecated.stored.image.set.StoredImageSet;
import com.dereekb.gae.model.deprecated.stored.image.set.search.document.query.StoredImageSetSearchBuilder.StoredImageSetSearch;
import com.dereekb.gae.model.extension.deprecated.search.document.search.service.model.impl.AbstractModelDocumentRequest;

/**
 * Search request for a {@link StoredImageSet}.
 *
 * @author dereekb
 *
 */
public class StoredImageSetSearchRequest extends AbstractModelDocumentRequest {

	public static final StoredImageSetSearchBuilder BUILDER = new StoredImageSetSearchBuilder("");

	private StoredImageSetSearch search;

	public StoredImageSetSearchRequest() {
		this.search = BUILDER.make();
	}

	public StoredImageSetSearch getSearch() {
		return this.search;
	}

	public void setSearch(StoredImageSetSearch search) throws IllegalArgumentException {
		if (search == null) {
			throw new IllegalArgumentException("Search cannot be null.");
		}

		this.search = search;
	}

	@Override
	public String toString() {
		return "StoredImageSetSearchRequest [search=" + this.search + "]";
	}

}
