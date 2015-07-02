package com.dereekb.gae.model.extension.links.components.system.impl.bidirectional;

import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.system.exception.UnknownReverseLinkException;

/**
 * Entry for a {@link BidirectionalLinkSystem} that describes how elements in a
 * type relate to each other.
 *
 * @author dereekb
 */
public interface BidirectionalLinkSystemEntry {

	/**
	 * @return The type this system represents.
	 */
	public String getLinkModelType();

	/**
	 * Checks if the target type should be updated with any changes that occur.
	 *
	 * @param info
	 *            {@link LinkInfo} of the link requesting it's reverse.
	 * @return true if the type is bi-directionally linked.
	 */
	public boolean isBidirectionallyLinked(LinkInfo info);

	/**
	 * Returns the reverse name for a link.
	 *
	 * @return Name of the reverse link that is expected on the other type.
	 * @throws UnknownReverseLinkException
	 *             if the reverse type is unknown.
	 */
	public String getReverseLinkName(LinkInfo info) throws UnknownReverseLinkException;

}
