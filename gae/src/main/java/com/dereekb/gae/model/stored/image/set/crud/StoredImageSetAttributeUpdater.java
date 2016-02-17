package com.dereekb.gae.model.stored.image.set.crud;

import com.dereekb.gae.model.crud.exception.AttributeFailureException;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;

public class StoredImageSetAttributeUpdater
        implements UpdateTaskDelegate<StoredImageSet> {

	@Override
	public void updateTarget(StoredImageSet target,
	                         StoredImageSet template) throws AttributeFailureException {

		if (template.getLabel() != null) {
			target.setLabel(template.getLabel());
		}

		if (template.getDetail() != null) {
			target.setDetail(template.getDetail());
		}

		if (template.getTags() != null) {
			target.setTags(template.getTags());
		}

	}

}
