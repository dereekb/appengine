package com.dereekb.gae.model.extension.links.components;

/**
 * Read-only {@link LinkInfo}.
 *
 * @author dereekb
 */
@Deprecated
public interface ReadOnlyLink
        extends LinkInfo {

	/**
	 * Returns a {@link LinkData} instance that describes this link.
	 *
	 * @return New {@link Relation} instance. Never null.
	 */
	public LinkData getLinkData();

}
