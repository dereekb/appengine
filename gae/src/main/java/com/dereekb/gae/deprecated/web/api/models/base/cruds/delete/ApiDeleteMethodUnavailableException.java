package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.delete;

public class ApiDeleteMethodUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApiDeleteMethodUnavailableException(String message) {
		super(message);
	}

	public static ApiDeleteMethodUnavailableException with(DeleteRequest<?> change) {
		String message = "Failed to delete for to type '" + change.getType() + "'.";
		return new ApiDeleteMethodUnavailableException(message);
	}

}