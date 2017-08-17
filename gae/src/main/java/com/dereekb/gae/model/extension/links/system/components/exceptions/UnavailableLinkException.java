package com.dereekb.gae.model.extension.links.system.components.exceptions;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when a requested link is unavailable.
 *
 * @author dereekb
 *
 */
public class UnavailableLinkException extends ApiLinkSystemException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "LINK_UNAVAILABLE";
	public static final String ERROR_TITLE = "Link Unavailable";

	private String link;

	public UnavailableLinkException(String link, String message) {
		super(message);
		this.setLink(link);
	}

	public static UnavailableLinkException withLink(String link) {
		return new UnavailableLinkException(link, "The link '" + link + "' was unavalble.");
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		if (link == null) {
			throw new IllegalArgumentException("link cannot be null.");
		}

		this.link = link;
	}

	// MARK: ApiLinkException
	@Override
	public ApiResponseError asResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
		
		error.setDetail(this.link);

		return error;
	}

	@Override
	public String toString() {
		return "UnavailableLinkException [link=" + this.link + "]";
	}

}
