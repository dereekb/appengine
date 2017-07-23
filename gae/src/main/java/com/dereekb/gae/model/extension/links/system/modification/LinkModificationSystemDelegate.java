package com.dereekb.gae.model.extension.links.system.modification;

import java.util.List;

/**
 * {@link LinkModificationSystem} delegate that generates a
 * {@link LinkModificationSystemDelegateInstance}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemDelegate {

	/**
	 * Performs some pre-modification tests to see if models exist, etc.
	 * <p>
	 * Modifies the input {@link MutableLinkModificationPair} instances with success or failure.
	 * 
	 * @param inputRequestChanges {@link List}. Never {@code null}.
	 */
	public void preTestModifications(List<MutableLinkModificationPair> inputRequestChanges);
	
	/**
	 * Generates a new instance of changes.
	 * 
	 * @return {@link LinkModificationSystemDelegateInstance}. Never
	 *         {@code null}.
	 */
	public LinkModificationSystemDelegateInstance makeInstance();

}
