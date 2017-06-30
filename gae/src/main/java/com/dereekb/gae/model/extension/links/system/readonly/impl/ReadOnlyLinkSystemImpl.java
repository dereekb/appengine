package com.dereekb.gae.model.extension.links.system.readonly.impl;

import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.exception.UnavailableLinkModelAccessorException;
import com.dereekb.gae.model.extension.links.system.readonly.LinkModelAccessor;
import com.dereekb.gae.model.extension.links.system.readonly.LinkSystem;
import com.dereekb.gae.model.extension.links.system.readonly.LinkSystemEntry;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

/**
 * {@link LinkSystem} implementation.
 * 
 * @author dereekb
 *
 */
public class ReadOnlyLinkSystemImpl
        implements LinkSystem {

	private CaseInsensitiveMap<LinkSystemEntry> entries;

	public ReadOnlyLinkSystemImpl(CaseInsensitiveMap<LinkSystemEntry> entries) {
		this.setEntries(entries);
	}

	public CaseInsensitiveMap<LinkSystemEntry> getEntries() {
		return this.entries;
	}

	public void setEntries(CaseInsensitiveMap<LinkSystemEntry> entries) {
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
	public LinkModelInfo loadLinkModelInfo(String type) throws UnavailableLinkModelException {
		return this.getEntry(type).loadLinkModelInfo();
	}

	@Override
	public LinkModelAccessor loadAccessor(String type) throws UnavailableLinkModelAccessorException {
		return this.getEntry(type).makeLinkModelAccessor();
	}

	private LinkSystemEntry getEntry(String type) throws UnavailableLinkModelAccessorException {
		LinkSystemEntry entry = this.entries.get(type);

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
