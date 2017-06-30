package com.dereekb.gae.model.extension.links.system.readonly;

import java.util.Set;

import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.exception.UnavailableLinkModelAccessorException;

/**
 * System used to read relationships of existing models.
 * 
 * @author dereekb
 *
 */
public interface LinkSystem {

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

	/**
	 * Loads a {@link LinkModelSet} with the given type.
	 *
	 * @param type
	 *            {@link String}. Never {@code null}.
	 * @return {@link LinkModelAccessor} instance. Never {@code null}.
	 * @throws UnavailableLinkModelAccessorException
	 *             if the requested type is unavailable.
	 */
	public LinkModelAccessor loadAccessor(String type) throws UnavailableLinkModelAccessorException;

}
