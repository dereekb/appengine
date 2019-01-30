package com.dereekb.gae.model.stored.blob.search.query;

import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;

/**
 * Implementation of {@link ModelQueryConverter} for {@link StoredBlobQuery}
 * instances.
 *
 * @author dereekb
 *
 */
public class StoredBlobQueryConverter
        implements ModelQueryConverter<StoredBlob, StoredBlobQuery> {

	private final static String INFO_TYPE_FIELD = "infoType";

	@Override
	public ObjectifyQueryRequest<StoredBlob> convertQuery(StoredBlobQuery query) {

		/*
		boolean recent = query.getRecent();
		String infoType = query.getInfoType();

		ObjectifyQueryRequest<StoredBlob> objectifyQuery = null;

		if (recent) {
			if (infoType != null) {
				objectifyQuery = this.queryBuilder.recentFieldEqualitySearch(INFO_TYPE_FIELD, infoType);
			} else {
				objectifyQuery = this.queryBuilder.recentSearch();
			}
		} else {
			objectifyQuery = this.queryBuilder.fieldEqualsQuery(INFO_TYPE_FIELD, infoType);
		}

		query.updateObjectifyQuery(objectifyQuery);
		*/

		return null;
	}

}
