package com.dereekb.gae.model.extension.links.components;

import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;

/**
 * Represents a relation between models. Depending on the implementation,
 * changes made through this {@link Link} object may only change one of those
 * models.
 *
 * @author dereekb
 *
 */
public interface Link
        extends LinkInfo {

	/**
	 * Adds a new relation to this link. When complete, this link should contain
	 * the relation.
	 *
	 * @param change
	 *            {@link Relation} to add. Never null.
	 *
	 * @throws RelationChangeException
	 *             if the relation cannot be changed.
	 * @throws UnavailableLinkException
	 *             if a required link cannot be loaded.
	 */
	public void addRelation(Relation change) throws RelationChangeException, UnavailableLinkException;

	/**
	 * Removes a relation from this link. When complete, this link shouldn't
	 * contain the relation anymore.
	 *
	 * @param change
	 *            {@link Relation} to remove. Never null.
	 *
	 * @throws RelationChangeException
	 *             if the relation cannot be changed.
	 */
	public void removeRelation(Relation change) throws RelationChangeException;

	/**
	 * Returns a {@link LinkData} instance that describes this link.
	 *
	 * @return New {@link Relation} instance. Never null.
	 */
	public LinkData getLinkData();

	/**
	 * Clears all relations from this link.
	 */
	public void clearRelations();

}
