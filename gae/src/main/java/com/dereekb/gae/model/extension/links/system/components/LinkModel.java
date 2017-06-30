package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * An interface for an existing model that provides information about its'
 * relationships.
 * 
 * @author dereekb
 */
public interface LinkModel
        extends UniqueModel, TypedLinkSystemComponent {

	/**
	 * Returns the {@link LinkModelInfo} for this model.
	 * 
	 * @return {@link LinkModelInfo}. Never {@code null}.
	 */
	public LinkModelInfo getLinkModelInfo();

	/**
	 * Returns the {@link Link} associated with the link name.
	 * 
	 * @param linkName
	 *            {@link LinkName}. Never {@code null}.
	 * @return {@link Link}. Never {@code null}.
	 * @throws UnavailableLinkException
	 *             thrown if the requested link does not exist.
	 */
	public Link getLink(String linkName) throws UnavailableLinkException;

}
