package com.dereekb.gae.model.extension.links.deprecated.exception;

import com.dereekb.gae.model.extension.links.deprecated.functions.LinksAction;

public class UnsupportedLinkChangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnsupportedLinkChangeException() {}

	public UnsupportedLinkChangeException(String message) {
		super(message);
	}

	public static UnsupportedLinkChangeException ExceptionWithType(String type,
	                                                               LinksAction operation) {
		String message = String.format("The type '%s' was unsupported with operation '%s'.", type, operation);
		UnsupportedLinkChangeException exception = new UnsupportedLinkChangeException(message);
		return exception;
	}

}
