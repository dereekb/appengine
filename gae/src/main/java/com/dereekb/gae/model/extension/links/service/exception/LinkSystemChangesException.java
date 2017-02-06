package com.dereekb.gae.model.extension.links.service.exception;

import java.util.List;

import com.dereekb.gae.model.extension.links.exception.LinkException;

/**
 * Contains a set of {@link LinkSystemChangeException} instances to be thrown together
 * in this container.
 *
 * @author dereekb
 */
public class LinkSystemChangesException extends LinkException {

	private static final long serialVersionUID = 1L;

	private List<LinkSystemChangeException> exceptions;

	public LinkSystemChangesException(List<LinkSystemChangeException> exceptions) {
		this.exceptions = exceptions;
	}

	public List<LinkSystemChangeException> getExceptions() {
		return this.exceptions;
	}

	public void setExceptions(List<LinkSystemChangeException> exceptions) {
		this.exceptions = exceptions;
	}

}
