package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.deprecated.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.system.exception.UnavailableLinkModelAccessorException;
import com.dereekb.gae.model.extension.links.system.readonly.LinkModelAccessor;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Interface used for modifying links/relationships of models.
 * 
 * @author dereekb
 * 
 * @deprecated This design won't work properly. The generic type information
 *             needs to be retained.
 */
@Deprecated
public interface MutableLinkSystem {

	/**
	 * Loads a {@link LinkModelSet} with the given type.
	 *
	 * @param type
	 *            Type to load.
	 * @return {@link LinkModelAccessor} instance. Never {@code null}.
	 * @throws UnavailableLinkModelAccessorException
	 *             if the requested type is unavailable.
	 */
	public <T extends UniqueModel> MutableLinkModelBuilder<T> loadBuilder(String type)
	        throws UnavailableLinkModelAccessorException;

}
