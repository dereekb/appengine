package com.dereekb.gae.model.extension.links.system.mutable.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link MutableLinkChange} implementation.
 * 
 * @author dereekb
 *
 */
public class MutableLinkChangeImpl
        implements MutableLinkChange {

	private MutableLinkChangeType linkChangeType;
	private List<ModelKey> keys;

	public static MutableLinkChangeImpl add(List<ModelKey> keys) throws IllegalArgumentException {
		if (keys == null || keys.isEmpty()) {
			throw new IllegalArgumentException("Keys cannot be null or empty.");
		}

		return new MutableLinkChangeImpl(MutableLinkChangeType.ADD, keys);
	}

	public static MutableLinkChangeImpl remove(List<ModelKey> keys) throws IllegalArgumentException {
		if (keys == null || keys.isEmpty()) {
			throw new IllegalArgumentException("Keys cannot be null or empty.");
		}

		return new MutableLinkChangeImpl(MutableLinkChangeType.REMOVE, keys);
	}

	public static MutableLinkChangeImpl set(List<ModelKey> keys) throws IllegalArgumentException {
		if (keys == null) {
			throw new IllegalArgumentException("Keys cannot be null.");
		}

		return new MutableLinkChangeImpl(MutableLinkChangeType.SET, keys);
	}

	public static MutableLinkChangeImpl clear() throws IllegalArgumentException {
		List<ModelKey> keys = Collections.emptyList();
		return new MutableLinkChangeImpl(MutableLinkChangeType.CLEAR, keys);
	}

	protected MutableLinkChangeImpl(MutableLinkChangeType linkChangeType, List<ModelKey> keys) {
		this.linkChangeType = linkChangeType;
		this.keys = keys;
	}

	// MARK: MutableLinkChange
	@Override
	public MutableLinkChangeType getLinkChangeType() {
		return this.linkChangeType;
	}

	@Override
	public List<ModelKey> getKeys() {
		return this.keys;
	}

	@Override
	public String toString() {
		return "MutableLinkChangeImpl [linkChangeType=" + this.linkChangeType + ", keys=" + this.keys + "]";
	}

}
