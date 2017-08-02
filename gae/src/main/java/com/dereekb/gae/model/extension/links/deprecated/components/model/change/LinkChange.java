package com.dereekb.gae.model.extension.links.components.model.change;

import java.util.Set;

import com.dereekb.gae.model.extension.links.deprecated.components.Link;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Represents {@link Relation} changes made to a given {@link Link}.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface LinkChange {

	/**
	 * Returns the {@link Link} for this type.
	 *
	 * @return {@link Link} containing these changes. Never {@code null}.
	 */
	public Link getLink();

	/**
	 * @return {@link Set} of all {@link ModelKey} values that were added to the
	 *         {@link Link}.
	 */
	public Set<ModelKey> getAddedKeys();

	/**
	 * @return {@link Set} of all {@link ModelKey} values that were removed from
	 *         the {@link Link}.
	 */
	public Set<ModelKey> getRemovedKeys();

	/**
	 * Whether or not any changes have been made to the {@link Link} returned by
	 * {@link #getLink()}.
	 *
	 * @return {@code true} if changes have been made.
	 */
	public boolean hasChanges();

}
