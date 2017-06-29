package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkImplDelegate} implementation for links with a single value.
 *
 * @author dereekb
 *
 */
public class SingleLink
        implements LinkImplDelegate {

	private final SingleLinkDelegate delegate;

	public SingleLink(SingleLinkDelegate delegate) {
		this.delegate = delegate;
	}

	public SingleLinkDelegate getDelegate() {
		return this.delegate;
	}

	// MARK: LinkImplDelegate
	@Override
	public List<ModelKey> keys() {
		ModelKey key = this.delegate.getKey();
		List<ModelKey> keys;

		if (key != null) {
			keys = new ArrayList<ModelKey>();
			keys.add(key);
		} else {
			keys = Collections.emptyList();
		}

		return keys;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Sets the new value, ONLY if the current key is {@code null}. Throws a
	 * {@link RelationChangeException} otherwise.
	 */
	@Override
	public boolean add(ModelKey key) throws RelationChangeException {
		boolean modified = false;
		ModelKey currentKey = this.delegate.getKey();

		if (currentKey == null) {
			this.delegate.setKey(key);
			modified = true;
		} else if (currentKey.equals(key) == false) {
			throw new RelationChangeException(currentKey, null, "Must clear this link before setting it.");
		}

		return modified;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Removes the current key, ONLY if it matches the current key value.
	 */
	@Override
	public boolean remove(ModelKey key) throws RelationChangeException {
		boolean modified = false;
		ModelKey currentKey = this.delegate.getKey();

		if (currentKey.equals(key)) {
			this.delegate.setKey(null);
			modified = true;
		}

		return modified;
	}

	/**
	 * Clears the current key value.
	 */
	@Override
	public Set<ModelKey> clear() {
		ModelKey currentKey = this.delegate.getKey();
		this.delegate.setKey(null);

		if (currentKey == null) {
			return Collections.emptySet();
		} else {
			Set<ModelKey> changed = new HashSet<ModelKey>(1);
			changed.add(currentKey);
			return changed;
		}
	}

	@Override
	public String toString() {
		return "SingleLink [delegate=" + this.delegate + "]";
	}

}
