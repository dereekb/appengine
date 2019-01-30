package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;

/**
 * {@link LinkModificationSystemChangeInstance} that wraps a set of queued
 * {@link LinkModificationSystemModelChange} changes and modifies an input model
 * when requested.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @deprecated Use {@link LinkModificationSystemModelChangeInstanceSet} instead.
 */
@Deprecated
public interface LinkModificationSystemModelChangesInstance {

	/**
	 * Queues a model change.
	 * 
	 * @param change
	 *            {@link LinkModificationSystemModelChange}. Never {@code null}.
	 */
	public void queueChange(LinkModificationSystemModelChange change);

	/**
	 * Applies all queue'd changes to the input link model.
	 * <p>
	 * All completed changes will then be retained for {@link #undoChanges()}.
	 * <p>
	 * If called again in the future for newly queue'd changes, it will return a
	 * new {@link LinkModificationResultSet} along with all prior changes.
	 * 
	 * @return {@link LinkModificationResultSet}.
	 */
	public LinkModificationResultSet applyChanges(MutableLinkModel linkModel);

	/**
	 * All changes
	 */
	public void undoChanges(MutableLinkModel linkModel);

}
