package com.dereekb.gae.model.stored.blob.search.document.index;

import com.dereekb.gae.model.deprecated.stored.blob.StoredBlob;
import com.dereekb.gae.model.deprecated.stored.blob.StoredBlobInfoType;
import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.staged.step.derivative.IncludedDocumentBuilderStep;

/**
 * {@link IncludedDocumentBuilderStep} implementation for
 * {@link StoredBlobInfoType} instances for adding {@link StoredBlob} types.
 *
 * @author dereekb
 *
 */
public class StoredBlobIncludedDocumentBuilderStep extends StoredBlobDocumentBuilderStep {

	public StoredBlobIncludedDocumentBuilderStep() {
		super(DERIVATIVE_FIELD_FORMAT, true);
	}

}
