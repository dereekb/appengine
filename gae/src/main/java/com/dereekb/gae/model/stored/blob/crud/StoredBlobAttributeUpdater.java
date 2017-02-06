package com.dereekb.gae.model.stored.blob.crud;

import com.dereekb.gae.model.crud.exception.AttributeFailureException;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.model.stored.blob.StoredBlob;

public class StoredBlobAttributeUpdater
        implements UpdateTaskDelegate<StoredBlob> {

	@Override
	public void updateTarget(StoredBlob target,
	                         StoredBlob template) throws AttributeFailureException {

	}

}
