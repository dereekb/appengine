package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.model.extension.links.system.components.exceptions.DynamicLinkInfoException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.NoRelationException;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

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
	 * Returns the model key type that this link stores.
	 * 
	 * @return {@link ModelKeyType}. Never {@code null}.
	 */
	public ModelKeyType getModelKeyType() throws DynamicLinkInfoException;

	/**
	 * Returns the model that is associated with this link.
	 * 
	 * @return {@link LinkModelInfo}. Never {@code null}.
	 */
	@Override
	public LinkModelInfo getLinkModelInfo();

	/**
	 * Returns info for the other side of the link, if it is available.
	 * 
	 * @return {@link Relation}. Never {@code null}.
	 * @throws NoRelationException
	 *             If there is no relation available.
	 * @throws DynamicLinkInfoException
	 *             if {@link #getLinkType()} returns {@link LinkType#DYNAMIC}.
	 */
	public Relation getRelationInfo() throws DynamicLinkInfoException, NoRelationException;

}
