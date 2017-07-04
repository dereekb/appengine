package com.dereekb.gae.model.extension.links.components.system.impl.bidirectional;

import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.exception.UnregisteredLinkTypeException;
import com.dereekb.gae.model.extension.links.components.system.exception.UnrelatedLinkException;
import com.dereekb.gae.model.extension.links.system.mutable.exception.NoReverseLinksException;

/**
 * Delegate for {@link BidirectionalLinkModelSet}.
 *
 * @author dereekb
 *
 */
public interface BidirectionalLinkModelSetDelegate {

	/**
	 * Loads a new {@link LinkModelSet} for the target type if the primary type
	 * has a bi-directional relation.
	 *
	 * @param primaryType
	 *            The primary type. Used for checking its relation with the
	 *            target type.
	 * @param targetType
	 *            The target type to load.
	 * @return {@link LinkModelSet} for the target type.
	 * @throws UnrelatedLinkException
	 *             if the target type is not related to the primary type.
	 * @throws UnregisteredLinkTypeException
	 *             if the target type is related, but that type is not
	 *             registered with the system.
	 */
	public LinkModelSet loadTargetTypeSet(String primaryType,
	                                       LinkInfo info)
	        throws UnregisteredLinkTypeException,
	            UnrelatedLinkException;

	/**
	 * Returns the name of the reverse link, if known.
	 *
	 * For example, a requesting link with the name "parent" might get "child"
	 * as the reverse.
	 *
	 * @param primaryType
	 *            type requesting the info
	 * @param info
	 *            {@link LinkInfo} for the requesting link.
	 * @return Reverse link name.
	 *
	 * @throws NoReverseLinksException
	 *             if there is no reverse link.
	 */
	public String getReverseLinkName(String primaryType,
	                                 LinkInfo info) throws NoReverseLinksException;

}
