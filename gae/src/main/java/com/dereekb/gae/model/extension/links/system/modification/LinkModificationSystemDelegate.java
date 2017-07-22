package com.dereekb.gae.model.extension.links.system.modification;

import java.util.List;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;

/**
 * {@link LinkModificationSystem} delegate that generates a
 * {@link LinkModificationSystemDelegateInstance}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemDelegate {

	/**
	 * Asserts that the provided modifications are possible/acceptable at that instant.
	 * <p>
	 * Checks to see whether or not target models exist/are readable.
	 * 
	 * @param modifications {@link List}. Never {@code null}.
	 * 
	 * @throws UnavailableLinkModelException
	 * @throws UnavailableModelException
	 * @throws UnavailableLinkException
	 */
	public void testModifications(List<LinkModification> modifications) throws UnavailableLinkModelException, UnavailableModelException, UnavailableLinkException;

	/**
	 * Generates a new instance of changes.
	 * 
	 * @return {@link LinkModificationSystemDelegateInstance}. Never
	 *         {@code null}.
	 */
	public LinkModificationSystemDelegateInstance makeInstance();

}
