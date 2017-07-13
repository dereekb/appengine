package com.dereekb.gae.model.extension.links.components.model;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelChange;

/**
 * Represents a single model.
 *
 * @author dereekb
 */
@Deprecated
public interface LinkModel
        extends ReadOnlyLinkModel {

	/**
	 * Returns the link with the given type.
	 *
	 * @param type
	 *            Name of the link.
	 * @return A {@link Link} corresponding to the type.
	 * @throws UnavailableLinkException
	 *             thrown if the link is unavailable.
	 */
	@Override
	public Link getLink(String name) throws UnavailableLinkException;

	/**
	 * Returns all links for the target model.
	 *
	 * @return {@link Collection} of all links for this model. Never null.
	 */
	@Override
	public Collection<? extends Link> getLinks();

	/**
	 * Returns a {@link LinkModelChange} containing all changes for the target
	 * model.
	 *
	 * @return {@link LinkModelChange} instance. Never {@code null}.
	 */
	public LinkModelChange getModelChanges();

}
