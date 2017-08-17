package com.dereekb.gae.model.extension.links.system.readonly;

import com.dereekb.gae.model.extension.links.system.components.LimitedLinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.TypedLinkSystemComponent;

/**
 * Used for generating an {@link LinkModelAccessor} instance for this
 * specific type.
 * 
 * @author dereekb
 *
 */
public interface LinkSystemEntry
        extends TypedLinkSystemComponent {

	/**
	 * Returns the {@link LinkModelInfo} for this type.
	 * 
	 * @return {@link LinkModelInfo}. Never {@code null}.
	 */
	public LimitedLinkModelInfo loadLinkModelInfo();

	/**
	 * Builds a new {@link LinkModelAccessor} for this type.
	 *
	 * @return {@link LinkModelAccessor} for this type. Never {@code null}.
	 */
	public LinkModelAccessor makeLinkModelAccessor();

}
