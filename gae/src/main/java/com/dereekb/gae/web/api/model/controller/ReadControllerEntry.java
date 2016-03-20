package com.dereekb.gae.web.api.model.controller;

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

	public ReadControllerEntryResponse read(ReadControllerEntryRequest request) throws AtomicOperationException;

}
