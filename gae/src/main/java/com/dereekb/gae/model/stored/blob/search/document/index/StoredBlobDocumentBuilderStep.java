package com.dereekb.gae.model.stored.blob.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.builder.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for adding
 * {@link StoredBlobDocumentBuilderStep}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class StoredBlobDocumentBuilderStep<T>
        implements StagedDocumentBuilderStep<StoredBlob> {

	@Override
	public void updateBuilder(StoredBlob model,
	                          Builder builder) {

		// Created Date
		Field.Builder creationField = SearchDocumentBuilderUtility.dateField("date", model.getDate());
		builder.addField(creationField);

		// Info Type Atom
		Field.Builder infoType = SearchDocumentBuilderUtility.atomField("infoType", model.getInfoType());
		builder.addField(infoType);

		// Info Identifier Atom
		Field.Builder infoIdentifier = SearchDocumentBuilderUtility.atomField("infoId",
		        model.getInfoIdentifier());
		builder.addField(infoIdentifier);

	}

}
