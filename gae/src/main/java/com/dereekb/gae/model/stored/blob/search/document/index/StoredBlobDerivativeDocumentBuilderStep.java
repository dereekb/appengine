package com.dereekb.gae.model.stored.blob.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.DerivativeDocumentBuilderStep;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobInfoType;

/**
 * {@link DerivativeDocumentBuilderStep} implementation for
 * {@link StoredBlobInfoType} instances for adding {@link StoredBlob} types.
 *
 * @author dereekb
 *
 */
public class StoredBlobDerivativeDocumentBuilderStep extends StoredBlobDocumentBuilderStep {

	public StoredBlobDerivativeDocumentBuilderStep() {
		super(DERIVATIVE_FIELD_FORMAT, true);
	}

}
