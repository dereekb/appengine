package com.dereekb.gae.utilities.collections.map.impl;

import java.util.Map;

import com.dereekb.gae.utilities.collections.map.CaseInsensitiveEntryContainer;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * {@link CaseInsensitiveEntryContainer} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            entry type
 */
public class CaseInsensitiveEntryContainerImpl<T>
        implements CaseInsensitiveEntryContainer<T> {

	private CaseInsensitiveMap<T> entries;

	public CaseInsensitiveEntryContainerImpl() {
		this(new CaseInsensitiveMap<T>());
	}

	public CaseInsensitiveEntryContainerImpl(Map<String, T> entries) {
		this.setEntries(entries);
	}

	public CaseInsensitiveEntryContainerImpl(CaseInsensitiveMap<T> entries) {
		this.setEntries(entries);
	}

	@Override
	public Map<String, T> getEntries() {
		return this.entries;
	}

	public final void setEntries(Map<String, T> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("entries cannot be null.");
		}

		this.setEntries(new CaseInsensitiveMap<T>(entries));
	}

	protected void setEntries(CaseInsensitiveMap<T> entries) {
		this.entries = entries;
	}

	@Override
	public T getEntryForType(String type) throws RuntimeException {
		T entry = this.entries.get(type);

		if (entry == null) {
			this.throwEntryDoesntExistException(type);
		}

		return entry;
	}

	public void addEntry(String key,
	                     T value) {
		this.entries.put(key, value);
	}

	protected void throwEntryDoesntExistException(String type) throws RuntimeException {
		throw new NullPointerException(type + " doesn't exist in the entries.");
	}

}
