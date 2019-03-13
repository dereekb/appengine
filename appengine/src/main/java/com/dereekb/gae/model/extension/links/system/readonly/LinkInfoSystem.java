package com.dereekb.gae.model.extension.links.system.readonly;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;

/**
 * System used to read {@link LinkModelInfo} of different types.
 * 
 * @author dereekb
 *
 */
public interface LinkInfoSystem {

	/**
	 * Returns a {@link Set} containing all available types.
	 *
	 * @return {@link Set} with all types. Never {@code null}.
	 */
	public Set<String> getAvailableSetTypes();

	/**
	 * Loads the {@link LinkModelInfo} for a specific type.
	 * 
	 * @param type
	 *            {@link String}. Never {@code null}.
	 * @return {@link LinkModelInfo}. Never {@code null}.
	 * @throws UnavailableLinkModelException
	 *             thrown if the type is not available.
	 */
	public LinkModelInfo loadLinkModelInfo(String type) throws UnavailableLinkModelException;

}
