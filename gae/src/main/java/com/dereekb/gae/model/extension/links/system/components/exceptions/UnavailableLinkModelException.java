package com.dereekb.gae.model.extension.links.system.components.exceptions;

/**
 * Thrown when a requested link model type is unavailable.
 * 
 * @author dereekb
 *
 */
public class UnavailableLinkModelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static UnavailableLinkModelException makeForType(String type) {
		return new UnavailableLinkModelException();
	}

}
