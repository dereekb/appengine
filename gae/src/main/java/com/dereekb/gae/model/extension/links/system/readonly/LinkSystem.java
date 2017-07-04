package com.dereekb.gae.model.extension.links.system.readonly;

import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.system.exception.UnavailableLinkModelAccessorException;

/**
 * System used to read relationships of existing models.
 * 
 * @author dereekb
 *
 */
public interface LinkSystem
        extends LinkInfoSystem {

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
