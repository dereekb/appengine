package com.dereekb.gae.model.stored.blob.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.builder.impl.derivative.DerivativeDocumentBuilderDelegate;
import com.dereekb.gae.model.extension.search.document.index.component.builder.impl.derivative.NoDerivativeTypeException;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobInfoType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link DerivativeDocumentBuilderDelegate} for reading the
 * {@link StoredBlob} identifier from {@link StoredBlobInfoType} instances.
 *
 * @author dereekb
 */
public final class StoredBlobDerivativeDocumentBuilderDelegate<T extends StoredBlobInfoType>
        implements DerivativeDocumentBuilderDelegate<T> {

	private final String type;

	public StoredBlobDerivativeDocumentBuilderDelegate(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public String readDerivativeType(T model) throws NoDerivativeTypeException {
		return this.type;
	}

	@Override
	public String readDerivativeIdentifier(T model) {
		ModelKey key = model.getStoredBlobKey();
		return key.keyAsString();
	}

}

