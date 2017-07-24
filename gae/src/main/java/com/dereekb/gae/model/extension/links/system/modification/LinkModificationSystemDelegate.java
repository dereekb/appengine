package com.dereekb.gae.model.extension.links.system.modification;

import java.util.List;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;

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
	 * 
	 * @param inputRequestChanges
	 *            {@link List}. Never {@code null}.
	 */
	public void preTestModifications(List<LinkModificationPreTestPair> testPairs);

	/**
	 * Creates a new entry instance.
	 * 
	 * @param type
	 *            {@link String}. Never {@code null}.
	 * @param config
	 *            {@link LinkModificationSystemEntryInstanceConfig}. Never
	 *            {@code null}.
	 * 
	 * @return {@link LinkModificationSystemEntryInstance}. Never {@code null}.
	 * @throws UnavailableLinkModelException
	 *             thrown if the input type is unavailable.
	 */
	public LinkModificationSystemEntryInstance makeInstanceForType(String type,
	                                                               LinkModificationSystemEntryInstanceConfig config)
	        throws UnavailableLinkModelException;

}
