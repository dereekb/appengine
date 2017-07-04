package com.dereekb.gae.model.extension.links.components.system.impl.bidirectional;

import java.util.List;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.mutable.exception.NoReverseLinksException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Delegate for a {@link BidirectionalLink} instance. These delegates are
 * crafted to be specific to a {@link BidirectionalLinkModel}, and should not be
 * shared.
 *
 * @author dereekb
 */
public interface BidirectionalLinkDelegate {

	/**
	 * Retrieves the reverse {@link Link} elements for the passed link name and
	 * type. Will always return a list, even if the link doesn't have any
	 * reverse types.
	 *
	 * @param info
	 *            {@link LinkInfo} of the link requesting the reverse links.
	 * @param keys
	 *            {@link ModelKey} of reverse elements.
	 * @return {@link List} of links corresponding to the reverse elements. If
	 *         the requesting link is only one-directional, then an empty list
	 *         is returned.
	 * 
	 * @throws NoReverseLinksException
	 *             if there are no reverse links for this link.
	 * @throws UnavailableLinkException
	 *             if the required links are unavailable.
	 */
	public List<Link> getReverseLinks(LinkInfo info,
	                                  List<ModelKey> keys) throws NoReverseLinksException, UnavailableLinkException;

}
