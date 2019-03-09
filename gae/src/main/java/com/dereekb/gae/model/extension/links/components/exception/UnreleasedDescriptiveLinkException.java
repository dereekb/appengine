package com.dereekb.gae.model.extension.links.components.exception;

import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedModel;
import com.dereekb.gae.model.extension.links.exception.LinkException;

/**
 * Thrown in instances where a {@link DescribedModel} is unsafely
 * changed.
 *
 * @author dereekb
 */
public class UnreleasedDescriptiveLinkException extends LinkException {

	private static final long serialVersionUID = 1L;

	public UnreleasedDescriptiveLinkException() {
		super();
	}

	public UnreleasedDescriptiveLinkException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnreleasedDescriptiveLinkException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnreleasedDescriptiveLinkException(String message) {
		super(message);
	}

	public UnreleasedDescriptiveLinkException(Throwable cause) {
		super(cause);
	}

}
