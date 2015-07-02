package com.thevisitcompany.gae.deprecated.model.mod.data.conversion;

import com.thevisitcompany.gae.deprecated.model.mod.data.SerializerPair;

@Deprecated
public class DataConversionFailureException extends RuntimeException {

	private static final String EXCEPTION_MESSAGE_FORMAT = "Failed converting pair: %s";
	private static final String CAUSE_EXCEPTION_MESSAGE_FORMAT = "Exception '%s' caused failure while converting pair: %s";
	private static final long serialVersionUID = 1L;

	private final SerializerPair<?, ?> pair;

	public DataConversionFailureException(String string) {
		super(string);
		this.pair = null;
	}

	public DataConversionFailureException(SerializerPair<?, ?> pair) {
		super(String.format(EXCEPTION_MESSAGE_FORMAT, pair));
		this.pair = pair;
	}

	public DataConversionFailureException(SerializerPair<?, ?> pair, String message) {
		super(message);
		this.pair = pair;
	}

	public DataConversionFailureException(SerializerPair<?, ?> pair, Throwable cause) {
		super(String.format(CAUSE_EXCEPTION_MESSAGE_FORMAT, cause, pair), cause);
		this.pair = pair;
	}

	public SerializerPair<?, ?> getPair() {
		return this.pair;
	}

}
