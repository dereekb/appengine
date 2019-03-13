package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.components.Link;
import com.dereekb.gae.model.extension.links.system.components.LinkModel;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;

/**
 * Mutable {@link LinkModel} that can modify links.
 * <p>
 * This type relies on an outside component to save any changes.
 * 
 * @author dereekb
 *
 */
public interface MutableLinkModel
        extends LinkModel {

	/**
	 * Returns the {@link Link} associated with the link name.
	 * 
	 * @param linkName
	 *            {@link LinkName}. Never {@code null}.
	 * @return {@link Link}. Never {@code null}.
	 * @throws UnavailableLinkException
	 *             thrown if the requested link does not exist.
	 */
	@Override
	public MutableLink getLink(String linkName) throws UnavailableLinkException;

}
