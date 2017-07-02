package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModelAccessorPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link LinkModificationSystemChangeInstance} that wraps a model and takes in
 * changes that are applied and can be undone.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface LinkModificationSystemModelInstance<T extends UniqueModel>
        extends MutableLinkModelAccessorPair<T>, LinkModificationSystemChangeInstance {

	/**
	 * Queues a model change.
	 * 
	 * @param change
	 *            {@link LinkModificationSystemModelChange}. Never {@code null}.
	 */
	public void queueChange(LinkModificationSystemModelChange change);

	/**
	 * Applies all queue'd changes.
	 * <p>
	 * All completed changes will then be retained for {@link #revertChanges()}.
	 * 
	 * @return {@link LinkModificationResultSet}.
	 */
	public LinkModificationResultSet applyChanges();

}
