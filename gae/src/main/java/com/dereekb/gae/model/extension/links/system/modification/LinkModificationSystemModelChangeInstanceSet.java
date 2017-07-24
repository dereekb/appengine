package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.modification.exception.LinkModelMismatchException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;

/**
 * Set of {@link LinkModificationSystemModelChangeInstance} values wrapped
 * together with a single model.
 * <p>
 * The instance is designed to be created on an idempotent loop, 
 * and as such should only use the provided link model.
 * 
 * @author dereekb
 * 
 * @see LinkModificationSystemModelChangeInstance for an individual instance.
 */
public interface LinkModificationSystemModelChangeInstanceSet {

	/**
	 * Returns the model.
	 * 
	 * @return {@link MutableLinkModel}. Never {@code null}.
	 */
	public MutableLinkModel getLinkModel();

	/**
	 * Applies all changes in this set to the target model.
	 * <p>
	 * Is only effective at being called once.
	 * 
	 * @return {@link LinkModificationResultSet}. Never {@code null}.
	 */
	public LinkModificationResultSet applyChanges();

	/**
	 * Reverts the changes made in this instance to the input link model, or to
	 * the previous model if none provided.
	 * <p>
	 * If {@link #applyChanges()} has not yet been called, no changes will be
	 * made.
	 * 
	 * @param linkModel
	 *            {@link MutableLinkModel}. Can be {@code null}.
	 * @return {@code true} if the model was modified.
	 * @throws LinkModelMismatchException
	 *             thrown if the input model does not match the previously
	 *             changed model.
	 */
	public boolean undoChanges(MutableLinkModel linkModel) throws LinkModelMismatchException;

}
