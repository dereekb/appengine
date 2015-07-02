package com.dereekb.gae.model.extension.links.service.impl;

import java.util.List;

/**
 * Contains a set of {@link LinkChangeException} instances to be thrown together
 * in this container.
 *
 * @author dereekb
 */
public class LinkChangesException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private List<LinkChangeException> exception;

	public LinkChangesException(List<LinkChangeException> exception) {
		this.exception = exception;
	}

	public List<LinkChangeException> getException() {
		return this.exception;
	}

	public void setException(List<LinkChangeException> exception) {
		this.exception = exception;
	}

}
