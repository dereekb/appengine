package com.dereekb.gae.model.extension.links.components.system.impl;

import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.LinkSystem;

/**
 * Entry for a {@link LinkSystem}.
 *
 * @author dereekb
 *
 */
public interface LinkSystemEntry {

	/**
	 * The entry type to use as a key. Must be unique to the type.
	 *
	 * @return Type to use as the entry.
	 */
	public String getLinkModelType();

	/**
	 * Builds a new {@link LinkModelSet} for this type.
	 *
	 * @return {@link LinkModelSet} for this type.
	 */
	public LinkModelSet makeSet();

}
