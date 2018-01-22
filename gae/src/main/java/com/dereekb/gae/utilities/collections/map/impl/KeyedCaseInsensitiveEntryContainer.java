package com.dereekb.gae.utilities.collections.map.impl;

import java.util.Map;

import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;
import com.dereekb.gae.utilities.misc.keyed.utility.KeyedUtility;

/**
 * {@link CaseInsensitiveMap} wrapper.
 *
 * @author dereekb
 *
 * @param <T>
 *            entry type
 */
public class KeyedCaseInsensitiveEntryContainer<T extends AlwaysKeyed<String>> extends CaseInsensitiveEntryContainer<T> {

	public KeyedCaseInsensitiveEntryContainer() {
		super();
	}

	public KeyedCaseInsensitiveEntryContainer(CaseInsensitiveMap<T> entries) {
		super(entries);
	}

	public KeyedCaseInsensitiveEntryContainer(Map<String, T> entries) {
		super(entries);
	}

	public KeyedCaseInsensitiveEntryContainer(Iterable<T> entries) {
		this.setEntries(entries);
	}

	public void setEntries(Iterable<T> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("Entries cannot be null.");
		}

		this.setEntries(KeyedUtility.toMap(entries));
	}

	@Override
	public String toString() {
		return "KeyedCaseInsensitiveEntryContainer [getEntries()=" + this.getEntries() + "]";
	}

}
