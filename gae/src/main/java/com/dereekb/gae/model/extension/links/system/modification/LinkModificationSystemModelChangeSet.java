package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;

/**
 * Set of {@link LinkModificationSystemModelChange}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemModelChangeSet {

	/**
	 * Whether or not all changes within this set are optional.
	 * 
	 * @return {@code true} if all changes are optional.
	 */
	public boolean isOptional();

	/**
	 * Makes a change set for the input model.
	 * 
	 * @param linkModel
	 *            {@link MutableLinkModel}. Never {@code null}.
	 * @return {@link LinkModificationSystemModelChangeInstance}. Never
	 *         {@code null}.
	 */
	public LinkModificationSystemModelChangeInstanceSet makeChangeSet(MutableLinkModel linkModel);

}
