package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;

/**
 * Abstract change that can be applied to models.
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
	 * Makes a change for the input model.
	 * 
	 * @param linkModel
	 *            {@link MutableLinkModel}. Never {@code null}.
	 * @return {@link LinkModificationSystemModelChangeInstance}. Never
	 *         {@code null}.
	 */
	public LinkModificationSystemModelChangeInstance makeChange(MutableLinkModel linkModel);

}
