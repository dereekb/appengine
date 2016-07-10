package com.dereekb.gae.model.extension.links.service.exception;

import com.dereekb.gae.model.extension.links.exception.LinkException;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;

/**
 * Thrown when a {@link LinkSystemChange} cannot be completed.
 *
 * @author dereekb
 *
 */
public class LinkSystemChangeException extends LinkException {

	private static final long serialVersionUID = 1L;

	private final LinkSystemChange change;

	public LinkSystemChangeException(LinkSystemChange change) {
		super();
		this.change = change;
	}

	public LinkSystemChangeException(LinkSystemChange change, String message, Throwable cause) {
		super(message, cause);
		this.change = change;
	}

	public LinkSystemChangeException(LinkSystemChange change, String message) {
		super(message);
		this.change = change;
	}

	public LinkSystemChangeException(LinkSystemChange change, Throwable cause) {
		super(cause);
		this.change = change;
	}

	public LinkSystemChange getChange() {
		return this.change;
	}

}
