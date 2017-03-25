package com.dereekb.gae.server.datastore.utility.impl;

import com.dereekb.gae.server.datastore.Deleter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.utility.ConfiguredDeleter;

/**
 * {@link ConfiguredDeleter} implementation.
 *
 * @author dereekb
 *
 */
public class ConfiguredDeleterImpl
        implements ConfiguredDeleter {

	private Deleter deleter;
	private Boolean asynchronous = true;

	public ConfiguredDeleterImpl(Deleter deleter) {
		this.deleter = deleter;
	}

	public ConfiguredDeleterImpl(Deleter deleter, Boolean asynchronous) {
		this.deleter = deleter;
		this.asynchronous = asynchronous;
	}

	public Deleter getDeleter() {
		return this.deleter;
	}

	public void setDeleter(Deleter deleter) {
		this.deleter = deleter;
	}

	public Boolean isAsynchronous() {
		return this.asynchronous;
	}

	public void setAsynchronous(Boolean asynchronous) {
		this.asynchronous = asynchronous;
	}

	@Override
	public void deleteWithKey(ModelKey key) {
		this.deleteWithKey(key, this.asynchronous);
	}

	@Override
	public void deleteWithKeys(Iterable<ModelKey> keys) {
		this.deleteWithKeys(keys, this.asynchronous);
	}

	@Override
	public void deleteWithKey(ModelKey key,
	                          Boolean async) {
		this.deleter.deleteWithKey(key, async);
	}

	@Override
	public void deleteWithKeys(Iterable<ModelKey> keys,
	                           Boolean async) {
		this.deleter.deleteWithKeys(keys, async);
	}

	@Override
	public String toString() {
		return "ConfiguredDeleterImpl [deleter=" + this.deleter + ", asynchronous=" + this.asynchronous + "]";
	}

}
