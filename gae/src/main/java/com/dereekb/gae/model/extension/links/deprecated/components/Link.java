package com.dereekb.gae.model.extension.links.components;

import com.dereekb.gae.model.extension.links.deprecated.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.deprecated.components.exception.UnavailableLinkException;

/**
 * Represents a relation between models. Depending on the implementation,
 * changes made through this {@link Link} object may only change one of those
 * models.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface Link
        extends ReadOnlyLink {

	/**
	 * Adds a new relation to this link.
	 * <p>
	 * When complete, this link should contain the relation.
	 *
	 * @param change
	 *            {@link Relation} to add. Never null.
	 * @return {@link RelationResult} for this change.
	 * @throws RelationChangeException
	 *             if the relation cannot be changed.
	 * @throws UnavailableLinkException
	 *             if a required link cannot be loaded.
	 */
	public RelationResult addRelation(Relation change) throws RelationChangeException, UnavailableLinkException;

	/**
	 * Removes a relation from this link. When complete, this link shouldn't
	 * contain the relation anymore.
	 *
	 * @param change
	 *            {@link Relation} to remove. Never null.
	 * @return {@link RelationResult} for this change.
	 * @throws RelationChangeException
	 *             if the relation cannot be changed.
	 */
	public RelationResult removeRelation(Relation change) throws RelationChangeException;

	/**
	 * Sets the relation for this link. Is a combination of
	 * {@link #clearRelations()}, followed by {@link #addRelation(Relation)}.
	 *
	 * @param change
	 *            {@link Relation} to set. Never null.
	 * @return {@link RelationResult} for this change.
	 * @throws RelationChangeException
	 *             if the relation cannot be changed.
	 * @throws UnavailableLinkException
	 *             if a required link cannot be loaded.
	 */
	public RelationResult setRelation(Relation change) throws RelationChangeException, UnavailableLinkException;

	/**
	 * Clears all relations from this link.
	 *
	 * @return {@link RelationResult} for this change.
	 * @throws RelationChangeException
	 *             if the relation cannot be changed.
	 */
	public RelationResult clearRelations() throws RelationChangeException;

}
