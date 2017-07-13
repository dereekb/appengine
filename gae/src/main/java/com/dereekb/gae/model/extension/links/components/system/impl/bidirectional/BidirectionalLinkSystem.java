package com.dereekb.gae.model.extension.links.components.system.impl.bidirectional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.model.extension.links.components.system.exception.UnregisteredLinkTypeException;
import com.dereekb.gae.model.extension.links.components.system.exception.UnrelatedLinkException;
import com.dereekb.gae.model.extension.links.system.mutable.exception.NoReverseLinksException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * Wraps a {@link LinkSystem} to provide bi-directional linking capabilities.
 *
 * @author dereekb
 */
@Deprecated
public class BidirectionalLinkSystem
        implements LinkSystem, BidirectionalLinkModelSetDelegate {

	/**
	 * {@link LinkSystem} implementation to wrap.
	 */
	private LinkSystem system;
	private final Map<String, BidirectionalLinkSystemEntry> entries = new CaseInsensitiveMap<BidirectionalLinkSystemEntry>();

	public BidirectionalLinkSystem(LinkSystem system) {
		this.system = system;
	}

	public BidirectionalLinkSystem(LinkSystem system, List<BidirectionalLinkSystemEntry> entries) {
		this(system);
		this.addEntries(entries);
	}

	public void addEntries(List<BidirectionalLinkSystemEntry> entries) {
		for (BidirectionalLinkSystemEntry entry : entries) {
			this.addEntry(entry);
		}
	}

	public void addEntry(BidirectionalLinkSystemEntry entry) {
		String type = entry.getLinkModelType();
		this.entries.put(type, entry);
	}

	private BidirectionalLinkSystemEntry getEntry(String key) {
		BidirectionalLinkSystemEntry entry = this.entries.get(key);

		if (entry == null) {
			throw new UnregisteredLinkTypeException();
		}

		return entry;
	}

	// LinkSystem
	@Override
	public Set<String> getAvailableSetTypes() {
		return this.system.getAvailableSetTypes();
	}

	@Override
	public LinkModelSet loadSet(String type) throws UnregisteredLinkTypeException {
		LinkModelSet primarySet = this.system.loadSet(type);
		return new BidirectionalLinkModelSet(type, this, primarySet);
	}

	@Override
	public LinkModelSet loadSet(String type,
	                            Collection<ModelKey> keys) throws UnregisteredLinkTypeException {
		LinkModelSet set = this.loadSet(type);
		set.loadModels(keys);
		return set;
	}

	// BidirectionalLinkModelSetDelegate
	@Override
	public LinkModelSet loadTargetTypeSet(String primaryType,
	                                       LinkInfo info) throws UnregisteredLinkTypeException, UnrelatedLinkException {

		BidirectionalLinkSystemEntry entry = this.getEntry(primaryType);
		LinkModelSet targetSet = null;

		if (entry.isBidirectionallyLinked(info)) {
			LinkTarget target = info.getLinkTarget();
			String targetType = target.getTargetType();
			targetSet = this.system.loadSet(targetType);
		} else {
			throw new UnrelatedLinkException();
		}

		return targetSet;
	}

	@Override
	public String getReverseLinkName(String primaryType,
	                                 LinkInfo info) throws NoReverseLinksException {
		BidirectionalLinkSystemEntry entry = this.getEntry(primaryType);
		return entry.getReverseLinkName(info);
	}

}
