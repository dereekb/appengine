package com.dereekb.gae.web.api.model.exception;

import java.util.List;

/**
 * Runtime exception for a model API.
 *
 * Thrown when one or more target models are missing.
 *
 * @author dereekb
 * @see {@link MissingRequiredResourceException} for other instances where
 *      dependent models are missing, but not actually the target models.
 */
@Deprecated
public class UnavailableModelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * List of identifiers as a string that are missing.
	 */
	private final List<String> missing;

	public UnavailableModelException(List<String> missing) {
		this.missing = missing;
	}

	public List<String> getMissing() {
		return this.missing;
	}

}
