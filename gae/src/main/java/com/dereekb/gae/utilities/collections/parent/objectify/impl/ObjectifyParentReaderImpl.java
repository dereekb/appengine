package com.dereekb.gae.utilities.collections.parent.objectify.impl;

import java.util.ArrayList;
import java.util.List;

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
		List<T> list = new ArrayList<T>();

		T current = child;

		while (current != null) {
			Key<T> parentKey = child.getParentKey();

			if (parentKey != null) {
				T parent = this.registry.get(parentKey);

				if (parent != null) {
					list.add(parent);
				}

				current = parent;
			} else {
				break;
			}
		}

		return list;
	}

	@Override
	public String toString() {
		return "ObjectifyParentIteratorImpl [registry=" + this.registry + "]";
	}

}
