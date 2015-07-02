package com.thevisitcompany.gae.deprecated.web.exceptions;

import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;

public class ModelRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final ApiResponse response;

	public ModelRequestException() {
		this(null);
	}

	public ModelRequestException(ApiResponse response) {
		this(response, null);
	}

	public ModelRequestException(ApiResponse response, String reason) {
		super(reason);

		if (this.response == null) {
			this.response = new ApiResponse();
		} else {
			this.response = response;
		}

		this.response.setSuccess(false);
	}

	public ApiResponse getResponse() {
		return response;
	}

}