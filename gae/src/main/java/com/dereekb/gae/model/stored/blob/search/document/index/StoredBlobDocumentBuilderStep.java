package com.dereekb.gae.model.stored.blob.search.document.index;

import java.util.Date;

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

		Long identifier = model.getIdentifier();
		Date date = model.getDate();

		String descriptorType = model.getDescriptorType();
		String descriptorId = model.getDescriptorId();

		// Place Identifier
		Field.Builder identifierField = SearchDocumentBuilderUtility.atomField("id", identifier.toString());
		builder.addField(identifierField);

		// Creation Date
		Field.Builder dateField = SearchDocumentBuilderUtility.dateField("date", date);
		builder.addField(dateField);

		// Descriptor Info
		Field.Builder descriptorField = SearchDocumentBuilderUtility.atomField("descriptorType", descriptorType);
		builder.addField(descriptorField);

		// Info Type Id
		Field.Builder descriptorIdField = SearchDocumentBuilderUtility.atomField("descriptorId", descriptorId);
		builder.addField(descriptorIdField);

	}

}
