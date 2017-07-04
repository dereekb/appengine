package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.components.LimitedLinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.TypedLinkSystemComponent;

/**
 * {@link MutableLinkSystem} entry.
 * 
 * @author dereekb
 *
 */
public interface MutableLinkSystemBuilderEntry
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

}
