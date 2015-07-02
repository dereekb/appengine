package com.dereekb.gae.model.stored.blob.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.components.DocumentSearchQueryConverter;
import com.dereekb.gae.server.search.document.DocumentQueryBuilder;
import com.dereekb.gae.server.search.document.fields.DocumentQueryAtomField;

/**
 * {@link DocumentSearchQueryConverter} for {@link StoredBlobDocumentQuery}.
 *
 * Uses AND for provided information types.
 *
 * @author dereekb
 *
 */
public final class StoredBlobDocumentQueryConverter
        implements DocumentSearchQueryConverter<StoredBlobDocumentQuery> {

	@Override
	public DocumentQueryBuilder convertQuery(StoredBlobDocumentQuery query) {
		DocumentQueryBuilder builder = new DocumentQueryBuilder();

		String infoType = query.getInfoType();
		String infoId = query.getInfoId();

		if (infoType != null) {
			DocumentQueryAtomField typeField = new DocumentQueryAtomField("infoType", infoType);
			builder.and(typeField);
		}

		if (infoId != null) {
			DocumentQueryAtomField idField = new DocumentQueryAtomField("infoId", infoId);
			builder.and(idField);
		}

		return builder;
	}

}
