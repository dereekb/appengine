package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.system.components.exceptions.NoRelationException;

/**
 * A reference on a type to another type.
 * 
 * @author dereekb
 *
 * @see Link for an instance that represents an existing model's link.
 */
public interface LinkInfo
        extends LimitedLinkInfo, TypedLinkSystemComponent {

	/**
	 * Returns the model that is associated with this link.
	 * 
	 * @return {@link LinkModel}. Never {@code null}.
	 */
	@Override
	public LinkModelInfo getLinkModelInfo();

	/**
	 * Returns info for the other side of the link, if it is available.
	 * 
	 * @return {@link Relation}. Never {@code null}.
	 * @throws NoRelationException
	 *             If there is no relation available.
	 */
	public Relation getRelationInfo() throws NoRelationException;

}
