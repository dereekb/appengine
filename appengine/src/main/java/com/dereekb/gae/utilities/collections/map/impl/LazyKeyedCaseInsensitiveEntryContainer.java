package com.dereekb.gae.utilities.collections.map.impl;

import java.util.Collection;

import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

/**
 * Abstract {@link KeyedCaseInsensitiveEntryContainer} that loads entries when
 * they are needed.
 *
 * @author dereekb
 *
 * @param <T>
 *            entry type
 */
public abstract class LazyKeyedCaseInsensitiveEntryContainer<T extends AlwaysKeyed<String>> extends KeyedCaseInsensitiveEntryContainer<T> {

	private boolean initialized = false;

	public LazyKeyedCaseInsensitiveEntryContainer() {
		super();
	}

	@Override
	public T getEntryForType(String type) {
		this.initialize();
		return super.getEntryForType(type);
	}

	public final void initialize() {
		if (this.initialized == false) {
			Collection<T> entries = this.makeEntries();
			this.setEntries(entries);
			this.initialized = true;
		}
	}

	protected abstract Collection<T> makeEntries();

}
