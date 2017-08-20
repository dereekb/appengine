package com.dereekb.gae.client.api.model.extension.link.exception;

import java.util.List;

// MARK: Interfaces
public interface ClientLinkSystemChangeErrorSet {

	/**
	 * Returns the primary type.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getType();

	/**
	 * Returns a list of errors.
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ClientLinkSystemChangeError> getErrors();

}