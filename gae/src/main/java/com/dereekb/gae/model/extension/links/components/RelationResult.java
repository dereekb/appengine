package com.dereekb.gae.model.extension.links.components;

import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used by {@link Link} for providing results about {@link Relation} changes.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface RelationResult {

	/**
	 * Returns {@link ModelKey} values for all successful/non-redundant changes.
	 * <p>
	 * For {@link Link#setRelation(Relation)}, this describes values that were
	 * removed, since all values in the {@link Relation} are also considered
	 * hits as they're set. This will include the values returned from
	 * {@link #getRedundant()}.
	 * <p>
	 * For {@link Link#addRelation(Relation)}, this describes values that were
	 * set to be added, and were not already linked.
	 * <p>
	 * For {@link Link#removeRelation(Relation)}, this describes values that
	 * were set to be removed, and were linked.
	 * <p>
	 * For {@link Link#clearRelations()}, this describes all removed values.
	 *
	 * @return {@link Set} of {@link ModelKey} for new changes. Never
	 *         {@code null}.
	 */
	public Set<ModelKey> getHits();

	/**
	 * Returns {@link ModelKey} values for all redundant changes.
	 * <p>
	 * For {@link Link#setRelation(Relation)}, this describes values that were
	 * already set before being cleared.
	 * <p>
	 * For {@link Link#addRelation(Relation)}, this describes values that were
	 * set to be added, but were already linked.
	 * <p>
	 * For {@link Link#removeRelation(Relation)}, this describes values that
	 * were set to be removed, but were not linked.
	 * <p>
	 * For {@link Link#clearRelations()}, this will return an empty {@link Set}.
	 *
	 * @return {@link Set} of {@link ModelKey} for redundant changes. Never
	 *         {@code null}.
	 */
	public Set<ModelKey> getRedundant();

}
