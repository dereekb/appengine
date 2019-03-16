package com.dereekb.gae.model.extension.links.system.mutable.exception;

/**
 * {@link NoReverseLinksException} extension for more specific cases.
 * 
 * @author dereekb
 *
 */
public class NoDynamicReverseLinksException extends NoReverseLinksException {

	private static final long serialVersionUID = 1L;

	public NoDynamicReverseLinksException(String dynamicLinkName) {
		super(dynamicLinkName);
	}

	public NoDynamicReverseLinksException(String dynamicLinkName, String dynamicLinkType) {
		super(dynamicLinkName, dynamicLinkType);
	}	

}
