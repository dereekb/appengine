package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.mutable.exception.NoReverseLinksException;

/**
 * Contains a map of bidirectional link names.
 * 
 * @author dereekb
 *
 */
public interface BidirectionalLinkNameMap {

	/**
	 * Checks if the target type should be updated with any changes that occur.
	 *
	 * @param linkName
	 *            {@link String}. Never {@code null}.
	 * @return {@code true} if the type is bi-directionally linked.
	 */
	public boolean isBidirectionallyLinked(String linkName);

	/**
	 * Returns the reverse name for a link.
	 *
	 * @return Name of the reverse link that is expected on the other type.
	 *
	 * @throws NoReverseLinksException
	 *             if there is no reverse link.
	 */
	public String getReverseLinkName(String linkName) throws NoReverseLinksException;

}
