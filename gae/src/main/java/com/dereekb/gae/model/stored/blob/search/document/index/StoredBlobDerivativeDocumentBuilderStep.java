package com.dereekb.gae.model.stored.blob.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.derivative.DerivativeDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobInfoType;
import com.dereekb.gae.model.stored.blob.StoredBlobType;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

/**
 * {@link DerivativeDocumentBuilderStep} implementation for
 * {@link StoredBlobInfoType} instances for adding {@link StoredBlob} types.
 *
 * @author dereekb
 *
 */
public final class StoredBlobDerivativeDocumentBuilderStep
        implements DerivativeDocumentBuilderStep<StoredBlobInfoType> {

	public final static String DEFAULT_FIELD_FORMAT = "SB_%s";

	private final String format;
	private final Getter<StoredBlob> blobGetter;

	public StoredBlobDerivativeDocumentBuilderStep(Getter<StoredBlob> blobGetter) {
		this(blobGetter, DEFAULT_FIELD_FORMAT);
	}

	public StoredBlobDerivativeDocumentBuilderStep(Getter<StoredBlob> blobGetter, String format) {
		this.blobGetter = blobGetter;
		this.format = format;
	}

	public String getFormat() {
		return this.format;
	}

	public Getter<StoredBlob> getBlobGetter() {
		return this.blobGetter;
	}

	@Override
	public void updateBuilder(String identifier,
	                          Builder builder) {

		ModelKey modelKey = new ModelKey(identifier);
		StoredBlob blob = this.blobGetter.get(modelKey);

		Date date = null;
		String blobIdString = null;
		String ending = null;

		if (blob != null) {
			Long blobIdentifier = blob.getIdentifier();
			blobIdString = blobIdentifier.toString();

			date = blob.getDate();
			StoredBlobType blobType = blob.getBlobType();
			ending = blobType.getEnding();
		}

		// Creation Date
		String dateFieldFormat = String.format(this.format, "date");
		Field.Builder dateField = SearchDocumentBuilderUtility.dateField(dateFieldFormat, date);
		builder.addField(dateField);

		// Blob Identifier
		String identifierFieldFormat = String.format(this.format, "id");
		Field.Builder identifierField = SearchDocumentBuilderUtility.atomField(identifierFieldFormat, blobIdString);
		builder.addField(identifierField);

		// Format Field
		String endingFieldFormat = String.format(this.format, "format");
		Field.Builder endingTypeField = SearchDocumentBuilderUtility.atomField(endingFieldFormat, ending);
		builder.addField(endingTypeField);

	}

}
