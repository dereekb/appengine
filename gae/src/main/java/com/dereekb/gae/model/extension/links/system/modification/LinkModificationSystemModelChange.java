package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.exception.internal.LinkModelMismatchException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;

/**
 * Abstract change that can be applied to models.
 * <p>
 * This is designed to be created on an idempotent loop,
 * and as such all created {@link LinkModificationSystemModelChangeInstance}
 * should also perform their actions idempotently.
 * 
 * @author dereekb
 *
 * @see LinkModificationSystemModelChangeBuilder
 */
public interface LinkModificationSystemModelChange {

	/**
	 * Whether or not all changes within this set are optional.
	 * 
	 * @return {@code true} if this change is optional.
	 */
	public boolean isOptional();

	/**
	 * Returns the linked modification pair.
	 * 
	 * @return {@link LinkModificationPair}. Never {@code null}.
	 */
	public LinkModificationPair getPair();

	/**
	 * Makes a change for the input model.
	 * 
	 * @param linkModel
	 *            {@link MutableLinkModel}. Never {@code null}.
	 * @return {@link LinkModificationSystemModelChangeInstance}. Never
	 *         {@code null}.
	 * 
	 * @throws LinkModelMismatchException
	 *             thrown if the input model is not the same as the expected
	 *             model.
	 */
	public LinkModificationSystemModelChangeInstance makeChangeInstance(MutableLinkModel linkModel)
	        throws LinkModelMismatchException;

}
