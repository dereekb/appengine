package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.create;

public class ApiCreateMethodUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApiCreateMethodUnavailableException(String message) {
		super(message);
	}

	public static ApiCreateMethodUnavailableException with(CreateRequest<?, ?> change) {
		String message = "Failed to create for to type '" + change.getType() + "'.";
		return new ApiCreateMethodUnavailableException(message);
	}

}