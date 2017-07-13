package com.dereekb.gae.model.extension.links.components.model;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.components.ReadOnlyLink;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.server.datastore.models.UniqueModel;

@Deprecated
public interface ReadOnlyLinkModel
        extends UniqueModel {

	/**
	 * Returns the model type.
	 *
	 * @return Model type. Never {@code null}.
	 */
	public String getType();

	/**
	 * Returns the link with the given type.
	 *
	 * @param type
	 *            Name of the link.
	 * @return A {@link ReadOnlyLink} corresponding to the type.
	 * @throws UnavailableLinkException
	 *             thrown if the link is unavailable.
	 */
	public ReadOnlyLink getLink(String name) throws UnavailableLinkException;

	/**
	 * Returns all links for the target model.
	 *
	 * @return {@link Collection} of all links for this model. Never null.
	 */
	public Collection<? extends ReadOnlyLink> getLinks();

}
