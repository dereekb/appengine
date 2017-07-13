package com.dereekb.gae.model.extension.links.components.exception;

/**
 * Exception reasons that describe how a link exception occured.
 * 
 * @author dereekb
 *
 */
@Deprecated
public enum LinkExceptionReason {

	/**
	 * The requested link was unavailable.
	 */
	LINK_UNAVAILABLE,

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
