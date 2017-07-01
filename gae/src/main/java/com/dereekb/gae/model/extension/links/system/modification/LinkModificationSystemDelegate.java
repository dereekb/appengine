package com.dereekb.gae.model.extension.links.system.modification;

/**
 * {@link LinkModificationSystem} delegate that generates a
 * {@link LinkModificationSystemDelegateInstance}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemDelegate {

	/**
	 * Generates a new instance of changes.
	 * 
	 * @return {@link LinkModificationSystemDelegateInstance}. Never
	 *         {@code null}.
	 */
	public LinkModificationSystemDelegateInstance makeInstance();

}
