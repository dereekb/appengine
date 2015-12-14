package com.dereekb.gae.model.stored.blob.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.google.appengine.api.search.Document.Builder;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for {@link StoredBlob}.
 *
 * @author dereekb
 */
public class StoredBlobDocumentBuilderStep
        implements StagedDocumentBuilderStep<StoredBlob> {

	@Override
	public void performStep(StoredBlob model,
	                        Builder builder) {

		// Date
		Date date = model.getDate();
		ModelDocumentBuilderUtility.addDate(date, builder);

		// Descriptors
		ModelDocumentBuilderUtility.addDescriptorInfo(model, builder);

	}

}
