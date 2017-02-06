package com.dereekb.gae.model.stored.blob.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.AbstractDerivableDocumentBuilderStep;
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
public class StoredBlobDocumentBuilderStep extends AbstractDerivableDocumentBuilderStep<StoredBlob> {

	public static final String ENDING_FIELD = "ending";
	public static final String TYPE_FIELD = "type";
	public static final String NAME_FIELD = "name";

	public final static String DERIVATIVE_PREFIX = "SB_";
	public final static String DERIVATIVE_FIELD_FORMAT = DERIVATIVE_PREFIX + "%s";

	public StoredBlobDocumentBuilderStep() {
		super();
	}

	public StoredBlobDocumentBuilderStep(String format, boolean inclusionStep) {
		super(format, inclusionStep);
	}

	// MARK: StagedDocumentBuilderStep
	@Override
	protected void performDescribedModelStep(StoredBlob model,
	                                         Builder builder) {
		// Name
		String blobName = model.getBlobName();
		SearchDocumentBuilderUtility.addText(NAME_FIELD, blobName, builder);

		// Type
		StoredBlobType blobType = model.getBlobType();
		String fileType = blobType.getFileType();
		SearchDocumentBuilderUtility.addAtom(TYPE_FIELD, fileType, builder);

	};

	@Override
	protected void performSharedStep(StoredBlob model,
	                                 Builder builder) {
		Date date = null;
		String ending = null;

		if (model != null) {
			date = model.getDate();

			StoredBlobType blobType = model.getBlobType();
			ending = blobType.getEnding();
		}

		// Creation Date
		ModelDocumentBuilderUtility.addDate(this.format, date, builder);

		// Format Field
		String endingName = String.format(this.format, ENDING_FIELD);
		SearchDocumentBuilderUtility.addAtom(endingName, ending, builder);

	}

}
