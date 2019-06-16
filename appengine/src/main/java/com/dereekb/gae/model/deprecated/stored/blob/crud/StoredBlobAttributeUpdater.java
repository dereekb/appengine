package com.dereekb.gae.model.stored.blob.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.model.deprecated.stored.blob.StoredBlob;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

public class StoredBlobAttributeUpdater
        implements UpdateTaskDelegate<StoredBlob> {

	@Override
	public void updateTarget(StoredBlob target,
	                         StoredBlob template) throws InvalidAttributeException {

	}

}
