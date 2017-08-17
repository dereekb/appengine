package com.dereekb.gae.web.api.model.crud.controller;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;

/**
 * Model entry for a {@link ReadController}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ReadControllerEntry {

	/**
	 * 
	 * @param request
	 *            {@link ReadControllerEntryRequest}. Never {@code null}.
	 * @return {@link ReadControllerEntryResponse}. Never {@code null}.
	 * @throws AtomicOperationException
	 *             thrown if the request is atomic and cannot be completed
	 *             atomically.
	 */
	public ReadControllerEntryResponse read(ReadControllerEntryRequest request) throws AtomicOperationException;

}
