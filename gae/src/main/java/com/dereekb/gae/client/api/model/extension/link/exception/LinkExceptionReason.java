package com.dereekb.gae.client.api.model.extension.link.exception;

/**
 * Exception reasons that describe how a link exception occured.
 * <p>
 * Each reason corresponds with an error code from another exception class.
 * 
 * @author dereekb
 *
 */
public enum LinkExceptionReason {

	/**
	 * The requested link was unavailable.
	 * 
	 * @see UnavailableLinkException.
	 */
	LINK_UNAVAILABLE,
	
	/**
	 * The requested model type was unavailable.
	 */
	LINK_MODEL_TYPE_UNAVAILABLE,

	/**
	 * The requested link failed being changed.
	 */
	CHANGE_FAILURE,

	/**
	 * Other reason.
	 */
	UNKNOWN_REASON;

	public static LinkExceptionReason fromErrorCode(String errorCode) {
		LinkExceptionReason reason = LinkExceptionReason.valueOf(errorCode);

		if (reason == null) {
			reason = LinkExceptionReason.UNKNOWN_REASON;
		}

		return reason;
	}

}
