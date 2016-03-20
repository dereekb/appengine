package com.dereekb.gae.model.extension.links.components.system.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.model.extension.links.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.components.system.exception.UnregisteredLinkTypeException;
import com.dereekb.gae.model.extension.links.components.system.impl.bidirectional.BidirectionalLinkSystem;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Simple implementation of {@link LinkSystem}.
 *
 * @author dereekb
 * @see {@link BidirectionalLinkSystem} for making sure both elements change at
 *      once.
 */
public class SimpleLinkSystem
        implements LinkSystem {

	private Map<String, LinkSystemEntry> entries = new HashMap<String, LinkSystemEntry>();

	public SimpleLinkSystem() {}

	public SimpleLinkSystem(List<LinkSystemEntry> entries) {
		this.addEntries(entries);
	}

	public void addEntries(List<LinkSystemEntry> entries) {
		for (LinkSystemEntry entry : entries) {
			this.addEntry(entry);
		}
	}

	public void addEntry(LinkSystemEntry entry) {
		String type = entry.getLinkModelType();
		this.entries.put(type, entry);
	}

	private LinkSystemEntry getEntry(String key) {
		LinkSystemEntry entry = this.entries.get(key);

		if (entry == null) {
			throw new UnregisteredLinkTypeException();
		}

		return entry;
	}

	// LinkSystem
	@Override
	public Set<String> getAvailableSetTypes() {
		return this.entries.keySet();
	}

	@Override
	public LinkModelSet loadSet(String type) throws UnregisteredLinkTypeException {
		LinkSystemEntry entry = this.getEntry(type);
		LinkModelSet set = entry.makeSet();

		if (set == null) {
			throw new IllegalArgumentException("System entry failed to create a new set.");
		}

		return set;
	}

	@Override
	public LinkModelSet loadSet(String type,
	                            Collection<ModelKey> keys) throws UnregisteredLinkTypeException {
		LinkModelSet set = this.loadSet(type);
		set.loadModels(keys);
		return set;
	}

}
