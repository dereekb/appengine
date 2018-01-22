package com.dereekb.gae.utilities.collections.map.impl;

import java.util.Map;

import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * {@link CaseInsensitiveMap} wrapper.
 *
 * @author dereekb
 *
 * @param <T>
 *            entry type
 */
public class CaseInsensitiveEntryContainer<T> {

	private CaseInsensitiveMap<T> entries;

	public CaseInsensitiveEntryContainer() {
		this(new CaseInsensitiveMap<T>());
	}

	public CaseInsensitiveEntryContainer(Map<String, T> entries) {
		this.setEntries(entries);
	}

	public CaseInsensitiveEntryContainer(CaseInsensitiveMap<T> entries) {
		this.setEntries(entries);
	}

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

	protected T getEntryForType(String type) throws RuntimeException {
		T entry = this.entries.get(type);

		if (entry == null) {
			this.throwEntryDoesntExistException(type);
		}

		return entry;
	}

	protected void throwEntryDoesntExistException(String type) throws RuntimeException {
		throw new NullPointerException(type + " doesn't exist in the entries.");
	}

}
