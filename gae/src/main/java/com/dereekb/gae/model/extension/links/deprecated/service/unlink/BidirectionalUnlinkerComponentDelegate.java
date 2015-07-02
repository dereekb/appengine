package com.dereekb.gae.model.extension.links.deprecated.service.unlink;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used by a {@link BidirectionalUnlinkerComponent} to retrieve the
 * {@link ModelKey}s that needs to be unlinked from.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface BidirectionalUnlinkerComponentDelegate<T> {

	public Collection<ModelKey> keysToUnlinkFrom(String type,
	                                             T object);

}
