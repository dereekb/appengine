package com.dereekb.gae.model.extension.links.service.impl;

import com.dereekb.gae.model.extension.links.service.LinkChange;

/**
 * Thrown when a {@link LinkChange} cannot be completed.
 *
 * @author dereekb
 *
 */
public class LinkChangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final LinkChange change;

	public LinkChangeException(LinkChange change) {
		super();
		this.change = change;
	}

	public LinkChangeException(LinkChange change, String message, Throwable cause) {
		super(message, cause);
		this.change = change;
	}

	public LinkChangeException(LinkChange change, String message) {
		super(message);
		this.change = change;
	}

	public LinkChangeException(LinkChange change, Throwable cause) {
		super(cause);
		this.change = change;
	}

	public LinkChange getChange() {
		return this.change;
	}

}
