package com.dereekb.gae.model.extension.links.components.model;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelChange;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Represents a single model.
 *
 * @author dereekb
 */
public interface LinkModel
        extends UniqueModel {

	/**
	 * Returns the model type.
	 *
	 * @return Model type. Never null.
	 */
	public String getType();

	/**
	 * Returns the link with the given type.
	 *
	 * @param type
	 *            Name of the link.
	 * @return A {@link Link} corresponding to the type.
	 * @throws UnavailableLinkException
	 *             thrown if the link is unavailable.
	 */
	public Link getLink(String name) throws UnavailableLinkException;

	/**
	 * Returns all links for the target model.
	 *
	 * @return {@link Collection} of all links for this model. Never null.
	 */
	public Collection<Link> getLinks();

	/**
	 * Returns a {@link LinkModelChange} containing all changes for the target
	 * model.
	 *
	 * @return {@link LinkModelChange} instance. Never {@code null}.
	 */
	public LinkModelChange getModelChanges();

}
