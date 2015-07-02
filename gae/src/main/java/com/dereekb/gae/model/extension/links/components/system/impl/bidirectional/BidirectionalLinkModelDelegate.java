package com.dereekb.gae.model.extension.links.components.system.impl.bidirectional;

import java.util.List;

import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Delegate for {@link BidirectionalLinkModel} instances.
 *
 * @author dereekb
 *
 */
public interface BidirectionalLinkModelDelegate {

	/**
	 * Retrieves the {@link LinkModel} elements for the passed type.
	 *
	 * @param info
	 *            {@link LinkInfo} of the requesting link.
	 * @param keys
	 *            {@link ModelKey} of reverse elements.
	 * @return {@link List} of {@link LinkModel} corresponding to the input
	 *         keys. If the type is only one-directional, then an empty
	 *         collection is returned.
	 */
	public List<LinkModel> getReverseLinkModels(LinkInfo info,
	                                            List<ModelKey> keys);

	/**
	 * Gets the reverse name for a link.
	 *
	 * For example, if the requestingLink is "parent", then the function will
	 * return "child". If there is no reverse, return null.
	 *
	 * @param info
	 *            {@link LinkInfo} of the requesting link.
	 * @return Reverse name. Null if the name is unsure.
	 */
	public String getReverseLinkName(LinkInfo info);

}
