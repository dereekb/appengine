package com.dereekb.gae.web.api.model.crud.controller.impl;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.crud.controller.ReadControllerExistsResponse;

/**
 * {@link ReadControllerExistsResponse} implementation.
 *
 * @author dereekb
 *
 */
public class ReadControllerExistsResponseImpl
        implements ReadControllerExistsResponse {

	private Collection<ModelKey> exists;
	private Collection<ModelKey> unavailableModelKeys;

	public ReadControllerExistsResponseImpl(Collection<ModelKey> exists, Collection<ModelKey> unavailableModelKeys) {
		this.setExists(exists);
		this.setUnavailableModelKeys(unavailableModelKeys);
	}

	// MARK: ReadControllerExistsResponseImpl
	@Override
	public Collection<ModelKey> getExists() {
		return this.exists;
	}

	public void setExists(Collection<ModelKey> exists) {
		if (exists == null) {
			throw new IllegalArgumentException("exists cannot be null.");
		}

		this.exists = exists;
	}

	@Override
	public Collection<ModelKey> getUnavailableModelKeys() {
		return this.unavailableModelKeys;
	}

	public void setUnavailableModelKeys(Collection<ModelKey> unavailableModelKeys) {
		if (unavailableModelKeys == null) {
			throw new IllegalArgumentException("unavailableModelKeys cannot be null.");
		}

		this.unavailableModelKeys = unavailableModelKeys;
	}

	@Override
	public String toString() {
		return "ReadControllerExistsResponseImpl [exists=" + this.exists + ", unavailableModelKeys="
		        + this.unavailableModelKeys + "]";
	}

}
