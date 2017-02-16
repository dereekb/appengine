package com.dereekb.gae.model.extension.links.components.exception;

import com.dereekb.gae.model.extension.links.exception.ApiLinkException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when a {@link Link} is unavailable.
 *
 * @author dereekb
 *
 */
public class UnavailableLinkException extends ApiLinkException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "LINK_UNAVAILABLE";
	public static final String ERROR_TITLE = "Link Unavailable";

	private String link;

	public UnavailableLinkException(String link) {
		this(link, null);
	}

	public UnavailableLinkException(String link, String message) {
		super(message);
		this.link = link;
	}

	public static UnavailableLinkException withLink(String link) {
		return new UnavailableLinkException(link, "The link '" + link + "' was unavalble.");
	}

	@Override
	public ApiResponseError getResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE);

		error.setTitle(ERROR_TITLE);
		error.setDetail(this.link);

		return error;
	}

	@Override
	public String toString() {
		return "UnavailableLinkException [link=" + this.link + "]";
	}

}
