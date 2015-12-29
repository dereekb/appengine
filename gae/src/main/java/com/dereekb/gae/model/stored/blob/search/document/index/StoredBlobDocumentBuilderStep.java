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

	@Override
	public void performStep(StoredBlob model,
	                        Builder builder) {

		// Type
		StoredBlobType blobType = model.getBlobType();
		String fileEnding = blobType.getEnding();
		String fileType = blobType.getFileType();

		SearchDocumentBuilderUtility.addAtom(ENDING_FIELD, fileEnding, builder);
		SearchDocumentBuilderUtility.addAtom(TYPE_FIELD, fileType, builder);

		// BlobName
		String blobName = model.getBlobName();
		SearchDocumentBuilderUtility.addAtom(NAME_FIELD, blobName, builder);

		// Date
		Date date = model.getDate();
		ModelDocumentBuilderUtility.addDate(date, builder);

		// Descriptors
		ModelDocumentBuilderUtility.addDescriptorInfo(model, builder);

	}

}
