package com.dereekb.gae.utilities.collections.tree.map.exceptions;

/**
 * Thrown when a literal value has nothing inside it.
 * 
 * @deprecated This is allowed now. This class is never called, as a result.
 * @author dereekb
 */
@Deprecated
public class MapTreeBuilderEmptyLiteralException extends RuntimeException {

	private static String ERROR_MESSAGE_FORMAT = "Empty map tree literal at index: %d";
	private static final long serialVersionUID = 1L;

	public MapTreeBuilderEmptyLiteralException(Integer index) {
		super(String.format(ERROR_MESSAGE_FORMAT, index));
	}

}
