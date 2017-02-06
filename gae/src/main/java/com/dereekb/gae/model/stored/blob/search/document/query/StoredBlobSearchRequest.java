package com.dereekb.gae.model.stored.blob.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractDescribedModelDocumentRequest;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.search.document.query.StoredBlobSearchBuilder.StoredBlobSearch;

/**
 * Search request for a {@link StoredBlob}.
 *
 * @author dereekb
 *
 */
public class StoredBlobSearchRequest extends AbstractDescribedModelDocumentRequest {

	public static final StoredBlobSearchBuilder BUILDER = new StoredBlobSearchBuilder("");

	private StoredBlobSearch search;

	public StoredBlobSearchRequest() {
		this.search = BUILDER.make();
	}

	public StoredBlobSearch getSearch() {
		return this.search;
    }

	public void setSearch(StoredBlobSearch search) throws IllegalArgumentException {
		if (search == null) {
			throw new IllegalArgumentException("Search cannot be null.");
		}

		this.search = search;
	}

	@Override
	public String toString() {
		return "StoredBlobSearchRequest [search=" + this.search + ", descriptor=" + this.descriptor + "]";
	}

}
