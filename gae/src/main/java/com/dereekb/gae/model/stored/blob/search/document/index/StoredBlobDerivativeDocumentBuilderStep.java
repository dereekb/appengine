package com.dereekb.gae.model.stored.blob.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.DerivativeDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobInfoType;
import com.dereekb.gae.model.stored.blob.StoredBlobType;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

/**
 * {@link DerivativeDocumentBuilderStep} implementation for
 * {@link StoredBlobInfoType} instances for adding {@link StoredBlob} types.
 *
 * @author dereekb
 *
 */
public class StoredBlobDerivativeDocumentBuilderStep
        implements StagedDocumentBuilderStep<StoredBlob> {

	public final static String DEFAULT_FIELD_FORMAT = "SB_%s";

	private String format;

	public StoredBlobDerivativeDocumentBuilderStep() {
		this(DEFAULT_FIELD_FORMAT);
	}

	public StoredBlobDerivativeDocumentBuilderStep(String format) {
		this.format = format;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public void performStep(StoredBlob model,
	                        Builder builder) {

		Date date = null;
		String id = null;
		String ending = null;

		if (model != null) {
			Long blobIdentifier = model.getIdentifier();
			id = blobIdentifier.toString();
			date = model.getDate();

			StoredBlobType blobType = model.getBlobType();
			ending = blobType.getEnding();
		}

		// Creation Date
		ModelDocumentBuilderUtility.addDateWithFormat(this.format, date, builder);

		// Identifier
		ModelDocumentBuilderUtility.addIdWithFormat(this.format, id, builder);

		// Format Field
		String endingFieldFormat = String.format(this.format, "format");
		Field.Builder endingTypeField = SearchDocumentBuilderUtility.atomField(endingFieldFormat, ending);
		builder.addField(endingTypeField);

	}

	@Override
	public String toString() {
		return "StoredBlobDerivativeDocumentBuilderStep [format=" + this.format + "]";
	}

}
