package com.dereekb.gae.model.extension.links.deprecated.functions.filters;

import com.dereekb.gae.model.extension.links.deprecated.functions.LinksAction;

public interface CanLinkFilterDelegate<T> {

	/**
	 * Checks whether or not the link can be processed.
	 * 
	 * @param object
	 * @param link
	 * @param action
	 * @return True if the link action can be processed.
	 */
	public boolean canLink(T object,
	                       String link,
	                       LinksAction action);

}
