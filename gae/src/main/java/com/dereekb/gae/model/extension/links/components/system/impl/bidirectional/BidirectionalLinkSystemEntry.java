package com.dereekb.gae.model.extension.links.components.system.impl.bidirectional;

import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.mutable.exception.NoReverseLinksException;

/**
 * Entry for a {@link BidirectionalLinkSystem} that describes how elements in a
 * type relate to each other.
 *
 * @author dereekb
 */
@Deprecated
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
	 * @return {@code true} if the type is bi-directionally linked.
	 */
	public boolean isBidirectionallyLinked(LinkInfo info);

	/**
	 * Returns the reverse name for a link.
	 *
	 * @return Name of the reverse link that is expected on the other type.
	 *
	 * @throws NoReverseLinksException
	 *             if there is no reverse link.
	 */
	public String getReverseLinkName(LinkInfo info) throws NoReverseLinksException;

}
