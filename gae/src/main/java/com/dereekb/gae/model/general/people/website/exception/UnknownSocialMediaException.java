package com.dereekb.gae.model.general.people.website.exception;

import com.dereekb.gae.model.general.people.website.utility.InterpretedWebsite;
import com.dereekb.gae.model.general.people.website.utility.WebsiteInterpreter;

/**
 * Thrown when a {@link WebsiteInterpreter} encounters an unknown social media
 * website.
 *
 * @author dereekb
 */
public class UnknownSocialMediaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String service;
	private final InterpretedWebsite website;

	public UnknownSocialMediaException(String service) {
		this.website = null;
		this.service = service;
	}

	public UnknownSocialMediaException(InterpretedWebsite website) {
		this.website = website;
		this.service = website.getType().getService();
	}

	public String getService() {
		return this.service;
	}

	public InterpretedWebsite getWebsite() {
		return this.website;
	}

}
