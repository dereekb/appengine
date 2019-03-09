package com.dereekb.gae.utilities.collections.parent.objectify.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.utilities.collections.parent.objectify.ObjectifyChild;
import com.dereekb.gae.utilities.collections.parent.objectify.ObjectifyParentReader;
import com.googlecode.objectify.Key;

/**
 * {@link ObjectifyParentReader} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            Objectify model
 */
public class ObjectifyParentReaderImpl<T extends ObjectifyChild<T>>
        implements ObjectifyParentReader<T> {

	private ObjectifyRegistry<T> registry;

	public ObjectifyParentReaderImpl(ObjectifyRegistry<T> registry) {
		this.setRegistry(registry);
	}

	public ObjectifyRegistry<T> getRegistry() {
		return this.registry;
	}

	public void setRegistry(ObjectifyRegistry<T> registry) {
		this.registry = registry;
	}

	// MARK: ObjectifyParentIterator
	@Override
	public List<T> getParentModels(T child) {
		Set<T> set = new HashSet<>();
		T current = child;

		while (current != null) {
			Key<T> parentKey = current.getParent();

			if (parentKey != null) {
				T parent = this.registry.get(parentKey);

				if (parent != null) {
					if (set.contains(parent)) {
						break; // Break if a "loop" is detected.
					} else {
						set.add(parent);
					}
				}

				current = parent;
			} else {
				break;
			}
		}

		return new ArrayList<>(set);
	}

	@Override
	public Set<ModelKey> getParentKeys(ModelKey childKey) throws UnavailableModelException {
		T child = this.registry.get(childKey);
		Set<ModelKey> parentKeys = null;

		if (child == null) {
			throw new UnavailableModelException(childKey);
		} else {
			parentKeys = this.getParentKeys(child);
		}

		return parentKeys;
	}

	@Override
	public Set<ModelKey> getParentKeys(T child) {
		List<T> parents = this.getParentModels(child);
		return ModelKey.makeModelKeySet(parents);
	}

	// NOTE: Add functions for detecting parent loops

	@Override
	public String toString() {
		return "ObjectifyParentIteratorImpl [registry=" + this.registry + "]";
	}

}
