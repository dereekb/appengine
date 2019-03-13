package com.dereekb.gae.utilities.collections.tree.map.exceptions;

public class MapTreeBuilderOpenParametersException extends RuntimeException {

	private static String ERROR_MESSAGE_FORMAT = "Open map tree parameter that started at index: %d";
	private static final long serialVersionUID = 1L;

	public MapTreeBuilderOpenParametersException(Integer index) {
		super(String.format(ERROR_MESSAGE_FORMAT, index));
	}

}
