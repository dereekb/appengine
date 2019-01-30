package com.dereekb.gae.model.extension.links.system.components;

/**
 * The type of {@link Link}.
 * 
 * @author dereekb
 *
 */
public enum LinkType {

	/**
	 * The {@link LinkInfo} for the link already refers to a set relation.
	 */
	STATIC,

	/**
	 * The {@link LinkInfo} for the link is ambiguous, and generally the link
	 * must be read to get a proper reading.
	 */
	DYNAMIC

}
