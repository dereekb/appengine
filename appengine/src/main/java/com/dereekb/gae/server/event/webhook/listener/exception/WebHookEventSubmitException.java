package com.dereekb.gae.server.event.webhook.listener.exception;

import com.dereekb.gae.server.event.webhook.listener.WebHookEventSubmitter;

/**
 * {@link RuntimeException} for {@link WebHookEventSubmitter}.
 *
 * @author dereekb
 *
 */
public class WebHookEventSubmitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WebHookEventSubmitException() {
		super();
	}

	public WebHookEventSubmitException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WebHookEventSubmitException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebHookEventSubmitException(String message) {
		super(message);
	}

	public WebHookEventSubmitException(Throwable cause) {
		super(cause);
	}

}
