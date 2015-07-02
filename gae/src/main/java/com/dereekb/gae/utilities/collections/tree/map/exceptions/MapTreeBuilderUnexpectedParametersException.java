package com.dereekb.gae.utilities.collections.tree.map.exceptions;

public class MapTreeBuilderUnexpectedParametersException extends RuntimeException {

	private static String ERROR_MESSAGE_FORMAT = "Start parameter value was unexpected at index: %d";
	private static final long serialVersionUID = 1L;

	public MapTreeBuilderUnexpectedParametersException(Integer index) {
		super(String.format(ERROR_MESSAGE_FORMAT, index));
	}

}
