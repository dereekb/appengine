package com.dereekb.gae.model.extension.links.system.mutable.impl;

import com.dereekb.gae.model.extension.links.system.mutable.BidirectionalLinkNameMap;
import com.dereekb.gae.model.extension.links.system.mutable.exception.NoReverseLinksException;

/**
 * {@link BidirectionalLinkNameMap} implementation that has no entries.
 * 
 * @author dereekb
 *
 */
public class EmptyBidirectionalLinkNameMapImpl
        implements BidirectionalLinkNameMap {

	public static final BidirectionalLinkNameMap SINGLETON = new EmptyBidirectionalLinkNameMapImpl();

	@Override
	public boolean isBidirectionallyLinked(String linkName) {
		return false;
	}

	@Override
	public String getReverseLinkName(String linkName) throws NoReverseLinksException {
		throw new NoReverseLinksException(linkName);
	}

	@Override
	public String getDynamicReverseLinkName(String dynamicLinkName,
	                                        String relationLinkType)
	        throws NoReverseLinksException {
		throw new NoReverseLinksException(dynamicLinkName, relationLinkType);
	}

}
