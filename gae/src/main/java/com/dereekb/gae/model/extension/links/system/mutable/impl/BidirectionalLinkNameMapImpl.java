package com.dereekb.gae.model.extension.links.system.mutable.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.links.system.mutable.BidirectionalLinkNameMap;
import com.dereekb.gae.model.extension.links.system.mutable.exception.NoReverseLinksException;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * {@link BidirectionalLinkNameMap} implementation.
 * 
 * @author dereekb
 *
 */
public class BidirectionalLinkNameMapImpl
        implements BidirectionalLinkNameMap {

	private CaseInsensitiveMap<String> linkMap;

	public BidirectionalLinkNameMapImpl(Map<String, String> linkNames) {
		this.setLinkMap(linkNames);
	}

	public Map<String, String> getLinkMap() {
		return this.linkMap;
	}

	public void setLinkMap(Map<String, String> linkMap) {
		if (linkMap == null) {
			throw new IllegalArgumentException("linkMap cannot be null.");
		}

		this.linkMap = new CaseInsensitiveMap<String>(linkMap);
	}

	// MARK: BidirectionalLinkNameMap
	@Override
	public boolean isBidirectionallyLinked(String linkName) {
		return this.linkMap.containsKey(linkName);
	}

	@Override
	public String getReverseLinkName(String linkName) throws NoReverseLinksException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "BidirectionalLinkNameMapImpl [linkMap=" + this.linkMap + "]";
	}

}
