package com.dereekb.gae.model.stored.blob.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobType;
import com.google.appengine.api.search.Document.Builder;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for {@link StoredBlob}.
 *
 * @author dereekb
 */
public class StoredBlobDocumentBuilderStep
        implements StagedDocumentBuilderStep<StoredBlob> {

	public static final String ENDING_FIELD = "ending";
	public static final String TYPE_FIELD = "type";
	public static final String NAME_FIELD = "name";

	public final static String DERIVATIVE_PREFIX = "SB_";
	public final static String DERIVATIVE_FIELD_FORMAT = DERIVATIVE_PREFIX + "%s";

	private String format;
	private boolean derivative;

	public StoredBlobDocumentBuilderStep() {
		this("%s", false);
	}

	public StoredBlobDocumentBuilderStep(String format, boolean derivative) {
		this.setFormat(format);
		this.setDerivative(derivative);
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isDerivative() {
		return this.derivative;
	}

	public void setDerivative(boolean derivative) {
		this.derivative = derivative;
	}

	// MARK: StagedDocumentBuilderStep
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
		ModelDocumentBuilderUtility.addDate(this.format, date, builder);

		if (this.derivative) {

			// Identifier
			ModelDocumentBuilderUtility.addId(this.format, id, builder);

			// Format Field
			String endingFieldFormat = String.format(this.format, StoredBlobDocumentBuilderStep.ENDING_FIELD);
			SearchDocumentBuilderUtility.addAtom(endingFieldFormat, ending, builder);

		} else {

			// Type
			StoredBlobType blobType = model.getBlobType();
			String fileEnding = blobType.getEnding();
			String fileType = blobType.getFileType();

			SearchDocumentBuilderUtility.addAtom(ENDING_FIELD, fileEnding, builder);
			SearchDocumentBuilderUtility.addAtom(TYPE_FIELD, fileType, builder);

			// BlobName
			String blobName = model.getBlobName();
			SearchDocumentBuilderUtility.addAtom(NAME_FIELD, blobName, builder);

			// Descriptors
			ModelDocumentBuilderUtility.addDescriptorInfo(model, builder);

		}

	}

}
