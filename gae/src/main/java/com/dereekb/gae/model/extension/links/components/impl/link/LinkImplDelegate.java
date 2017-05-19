package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Delegate for {@link LinkImpl}.
 *
 * @author dereekb
 */
public interface LinkImplDelegate {

	/**
	 * @return {@link List} of {@link ModelKey} values within this link. Never
	 *         {@code null}.
	 * 
	 * @throws RelationChangeException
	 *             if the change fails.
	 */
	public List<ModelKey> keys();

	/**
	 * Adds the {@link ModelKey} to the link.
	 *
	 * @param key
	 * @return {@code true} if this change was not redundant.
	 * 
	 * @throws RelationChangeException
	 *             if the change fails.
	 */
	public boolean add(ModelKey key) throws RelationChangeException;

	/**
	 * Removes the {@link ModelKey} to the link.
	 *
	 * @param key
	 * @return {@code true} if this change was not redundant.
	 * 
	 * @throws RelationChangeException
	 *             if the change fails.
	 */
	public boolean remove(ModelKey key) throws RelationChangeException;

	/**
	 * @return {@link Set} of {@link ModelKey} values cleared. Never
	 *         {@code null}.
	 * 
	 * @throws RelationChangeException
	 *             if the change fails.
	 */
	public Set<ModelKey> clear() throws RelationChangeException;

}
