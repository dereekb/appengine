package com.dereekb.gae.model.crud.deprecated.function.filters.misc;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Delegate for an {@link ExistsFilter} instance.
 *
 * @author dereekb
 */
public interface ExistsFilterDelegate<W> {

	public Collection<ModelKey> getKeysForExistCheck(W item);

}
