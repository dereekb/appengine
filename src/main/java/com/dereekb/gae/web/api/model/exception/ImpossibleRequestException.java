package com.dereekb.gae.web.api.model.exception;

import java.util.List;

/**
 * Used by some services to specify that full filling the request is impossible.
 *
 * @author dereekb
 *
 */
public class ImpossibleRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private List<String> resources;
	private String message;

	public List<String> getResources() {
		return this.resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
