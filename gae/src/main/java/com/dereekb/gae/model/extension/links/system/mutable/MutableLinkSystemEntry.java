package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.components.LimitedLinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.TypedLinkSystemComponent;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;

/**
 * {@link MutableLinkSystem} entry.
 * 
 * @author dereekb
 *
 */
public interface MutableLinkSystemEntry
        extends TypedLinkSystemComponent {

	/**
	 * Returns the {@link LinkModelInfo} for this type.
	 * 
	 * @return {@link LinkModelInfo}. Never {@code null}.
	 */
	public LimitedLinkModelInfo loadLinkModelInfo();

	/**
	 * Returns a map of bidirectional links.
	 * 
	 * @return {@link BidirectionalLinkNameMap}. Never {@code null}.
	 */
	public BidirectionalLinkNameMap getBidirectionalMap();

	/**
	 * Creates a new link accessor for the specified type.
	 * 
	 * @param linkName
	 *            {@link String}. Never {@code null}.
	 * @return {@link MutableLinkAccessor}. Never {@code null}.
	 * @throws UnavailableLinkException
	 *             thrown if the link is unavailable
	 */
	public MutableLinkAccessor makeLinkAccessor(String linkName) throws UnavailableLinkException;

}
