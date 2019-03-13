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
	 * Reads the models.
	 *
	 * @param request
	 *            {@link ReadControllerEntryRequest}. Never {@code null}.
	 * @return {@link ReadControllerEntryResponse}. Never {@code null}.
	 * @throws AtomicOperationException
	 *             thrown if the request is atomic and cannot be completed
	 *             atomically.
	 */
	public ReadControllerEntryResponse read(ReadControllerEntryRequest request) throws AtomicOperationException;

	/**
	 * Checks if the models exist.
	 *
	 * @param request
	 *            {@link ReadControllerExistsRequest}. Never {@code null}.
	 * @return {@link ReadControllerExistsResponse}. Never {@code null}.
	 * @throws AtomicOperationException
	 *             thrown if the request is atomic and cannot be completed
	 *             atomically.
	 */
	public ReadControllerExistsResponse exists(ReadControllerExistsRequest request) throws AtomicOperationException;

}
