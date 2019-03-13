package com.dereekb.gae.model.extension.links.system.mutable.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.Link;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;

/**
 * {@link MutableLinkChange} implementation.
 * 
 * @author dereekb
 *
 */
public class MutableLinkChangeImpl
        implements MutableLinkChange {

	protected final MutableLinkChangeType linkChangeType;
	protected final Set<ModelKey> keys;

	public static MutableLinkChangeImpl add(Collection<ModelKey> keys) throws IllegalArgumentException {
		if (keys == null || keys.isEmpty()) {
			throw new IllegalArgumentException("Keys cannot be null or empty.");
		}

		return new MutableLinkChangeImpl(MutableLinkChangeType.ADD, keys);
	}

	public static MutableLinkChangeImpl remove(Collection<ModelKey> keys) throws IllegalArgumentException {
		if (keys == null || keys.isEmpty()) {
			throw new IllegalArgumentException("Keys cannot be null or empty.");
		}

		return new MutableLinkChangeImpl(MutableLinkChangeType.REMOVE, keys);
	}

	public static MutableLinkChangeImpl setOrClear(Collection<ModelKey> keys) throws IllegalArgumentException {
		if (keys != null && keys.isEmpty()) {
			return clear();
		} else {
			return set(keys);
		}
	}

	public static MutableLinkChangeImpl set(Collection<ModelKey> keys) throws IllegalArgumentException {
		if (keys == null || keys.isEmpty()) {
			throw new IllegalArgumentException("Keys cannot be null or empty.");
		}

		return new MutableLinkChangeImpl(MutableLinkChangeType.SET, keys);
	}

	public static MutableLinkChangeImpl clear() {
		Set<ModelKey> keys = Collections.emptySet();
		return new MutableLinkChangeImpl(MutableLinkChangeType.CLEAR, keys);
	}

	public static MutableLinkChange none() {
		Set<ModelKey> keys = Collections.emptySet();
		return new MutableLinkChangeImpl(MutableLinkChangeType.NONE, keys);
	}

	public static MutableLinkChangeImpl make(MutableLinkChangeType linkChangeType,
	                                         ModelKey key) {
		return make(linkChangeType, SetUtility.wrap(key));
	}

	public static MutableLinkChangeImpl make(MutableLinkChangeType linkChangeType,
	                                         Collection<ModelKey> keys) {
		MutableLinkChangeImpl change = new MutableLinkChangeImpl(linkChangeType, keys);
		change.assertKeysAndType();
		return change;
	}

	/**
	 * Makes a reverse change for a {@link MutableLinkChange}.
	 * 
	 * @param change
	 *            {@link MutableLinkChange}. Never {@code null}.
	 * @param result
	 *            {@link MutableLinkChangeResult}. Never {@code null}.
	 * @return {@link MutableLinkChangeImpl}. Never {@code null}.
	 */
	public static MutableLinkChange makeUndo(Link currentLink,
	                                         MutableLinkChange previousChange,
	                                         MutableLinkChangeResult result) {
		return MutableLinkChangeUndoBuilderImpl.SINGLETON.makeUndo(currentLink, previousChange, result);
	}

	protected MutableLinkChangeImpl(MutableLinkChangeType linkChangeType, Collection<ModelKey> keys) {
		this.linkChangeType = linkChangeType;
		this.keys = new HashSet<ModelKey>(keys);
	}

	protected void assertKeysAndType() {
		if (this.linkChangeType == null) {
			throw new IllegalArgumentException("linkChangeType cannot be null.");
		}

		if (this.keys == null) {
			throw new IllegalArgumentException("keys cannot be null.");
		}
	}

	// MARK: MutableLinkChange
	@Override
	public MutableLinkChangeType getLinkChangeType() {
		return this.linkChangeType;
	}

	@Override
	public Set<ModelKey> getKeys() {
		return this.keys;
	}

	@Override
	public String getDynamicLinkModelType() {
		return null;
	}
	
	@Override
	public String toString() {
		return "MutableLinkChangeImpl [linkChangeType=" + this.linkChangeType + ", keys=" + this.keys + "]";
	}

}
