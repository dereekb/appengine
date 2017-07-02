package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;

/**
 * Abstract change that can be applied to models.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemModelChange {

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
