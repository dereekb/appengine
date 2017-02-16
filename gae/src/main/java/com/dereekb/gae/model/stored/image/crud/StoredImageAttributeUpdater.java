package com.dereekb.gae.model.stored.image.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

public class StoredImageAttributeUpdater
        implements UpdateTaskDelegate<StoredImage> {

	@Override
	public void updateTarget(StoredImage target,
	                         StoredImage template) throws InvalidAttributeException {

		if (template.getName() != null) {
			target.setName(template.getName());
		}

		if (template.getSummary() != null) {
			target.setSummary(template.getSummary());
		}

		if (template.getTags() != null) {
			target.setTags(template.getTags());
		}

	}

}
