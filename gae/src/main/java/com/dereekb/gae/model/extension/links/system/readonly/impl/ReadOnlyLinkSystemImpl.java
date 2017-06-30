package com.dereekb.gae.model.extension.links.system.readonly.impl;

import com.dereekb.gae.model.extension.links.system.exception.UnavailableLinkModelAccessorException;
import com.dereekb.gae.model.extension.links.system.readonly.LinkModelAccessor;
import com.dereekb.gae.model.extension.links.system.readonly.ReadOnlyLinkSystem;
import com.dereekb.gae.model.extension.links.system.readonly.ReadOnlyLinkSystemEntry;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

/**
 * {@link ReadOnlyLinkSystem} implementation.
 * 
 * @author dereekb
 *
 */
public class ReadOnlyLinkSystemImpl
        implements ReadOnlyLinkSystem {

	private CaseInsensitiveMap<ReadOnlyLinkSystemEntry> entries;

	public ReadOnlyLinkSystemImpl(CaseInsensitiveMap<ReadOnlyLinkSystemEntry> entries) {
		this.setEntries(entries);
	}

	public CaseInsensitiveMap<ReadOnlyLinkSystemEntry> getEntries() {
		return this.entries;
	}

	public void setEntries(CaseInsensitiveMap<ReadOnlyLinkSystemEntry> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("entries cannot be null.");
		}

		this.entries = entries;
	}

	// MARK: ReadOnlyLinkSystem
	@Override
	public CaseInsensitiveSet getAvailableSetTypes() {
		return this.entries.keySet();
	}

	@Override
	public LinkModelAccessor loadAccessor(String type) throws UnavailableLinkModelAccessorException {
		return this.getEntry(type).makeReadOnlyLinkModelAccessor();
	}

	private ReadOnlyLinkSystemEntry getEntry(String type) throws UnavailableLinkModelAccessorException {
		ReadOnlyLinkSystemEntry entry = this.entries.get(type);

		if (entry == null) {
			throw new UnavailableLinkModelAccessorException();
		}

		return entry;
	}

	@Override
	public String toString() {
		return "ReadOnlyLinkSystemImpl [entries=" + this.entries + "]";
	}

}
