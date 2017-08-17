package com.dereekb.gae.model.extension.links.system.mutable.exception;

import com.dereekb.gae.model.extension.links.system.components.exceptions.LinkSystemException;

/**
 * Thrown when a {@link Link} is unavailable.
 *
 * @author dereekb
 *
 */
public class NoReverseLinksException extends LinkSystemException {

	private static final long serialVersionUID = 1L;

	private String linkName;
	private String dynamicLinkType;

	public NoReverseLinksException(String linkName) {
		super();
		this.linkName = linkName;
	}
	
	public NoReverseLinksException(String linkName, String dynamicLinkType) {
		super();
		this.linkName = linkName;
		this.dynamicLinkType = dynamicLinkType;
	}

	public String getLinkName() {
		return this.linkName;
	}
	
	public String getDynamicLinkType() {
		return this.dynamicLinkType;
	}

}
