package com.dereekb.gae.utilities.collections.tree.map.exceptions;

public class MapTreeBuilderOpenLiteralException extends RuntimeException {

	private static String ERROR_MESSAGE_FORMAT = "Open map tree literal that started at index: %d";
	private static final long serialVersionUID = 1L;

	public MapTreeBuilderOpenLiteralException(Integer index) {
		super(String.format(ERROR_MESSAGE_FORMAT, index));
	}

}
