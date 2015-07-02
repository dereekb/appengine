package com.thevisitcompany.gae.deprecated.web.api.models.support.links;

public class ApiLinksChangeUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApiLinksChangeUnavailableException(String message) {
		super(message);
	}

	public static ApiLinksChangeUnavailableException with(ApiLinksChange<?> change) {
		String message = "Failed to respond to type '" + change.getType() + "' and action '" + change.getAction()
		        + "'.";
		return new ApiLinksChangeUnavailableException(message);
	}

}