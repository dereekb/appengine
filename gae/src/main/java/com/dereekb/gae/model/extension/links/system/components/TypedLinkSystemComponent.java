package com.dereekb.gae.model.extension.links.system.components;

/**
 * Abstract link system component that has a type.
 * 
 * @author dereekb
 *
 */
public abstract interface TypedLinkSystemComponent {

	/**
	 * Returns the type this component represents.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getLinkModelType();

}
