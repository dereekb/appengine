package com.dereekb.gae.model.extension.links.components.system.exception;

import com.dereekb.gae.model.extension.links.deprecated.components.LinkInfo;


/**
 * Thrown when the reverse for a specified link is unknown.
 *
 * @author dereekb
 *
 */
@Deprecated
public class UnknownReverseLinkException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LinkInfo info;

	public UnknownReverseLinkException(LinkInfo info) {
		this.info = info;
	}

	public LinkInfo getInfo() {
		return this.info;
	}

	public void setInfo(LinkInfo info) {
		this.info = info;
	}

}
