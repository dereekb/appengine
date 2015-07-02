package com.dereekb.gae.utilities.collections.tree.map.exceptions;

public class MapTreeBuilderUnexpectedTokenException extends RuntimeException {

	private static String ERROR_MESSAGE_FORMAT = "Unexpected token at index: %d";
	private static final long serialVersionUID = 1L;

	public MapTreeBuilderUnexpectedTokenException(Integer index) {
		super(String.format(ERROR_MESSAGE_FORMAT, index));
	}

}
