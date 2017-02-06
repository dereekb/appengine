package com.dereekb.gae.model.extension.links.components.model.change;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.links.components.model.LinkModel;

/**
 * Represents changes made to a given {@link LinkModel}.
 *
 * @author dereekb
 *
 */
public interface LinkModelChange {

	/**
	 * Returns the {@link LinkModel} for this type.
	 *
	 * @return {@link LinkModel} containing these changes.
	 */
	public LinkModel getModel();

	/**
	 * Returns a {@link List} of {@link LinkChange} containers. Each
	 * {@link LinkChange} in the {@link List} should include at-least one
	 * change.
	 *
	 * @return {@link List} of {@link LinkModelChange} for changes made to this
	 *         set. The returned value is empty if no changes were made to the
	 *         {@link LinkModel} returned by {{@link #getModel()}.
	 */
	public Collection<LinkChange> getLinkChanges();

	/**
	 * @param name
	 *            name of the link to load.
	 * @return {@link LinkChange} for the target link.
	 * @throws IllegalArgumentException
	 *             if the input link is null.
	 */
	public LinkChange getChangesForLink(String name) throws IllegalArgumentException;

	/**
	 * @return {@code true} if changes have been made.
	 */
	public boolean hasChanges();

}
