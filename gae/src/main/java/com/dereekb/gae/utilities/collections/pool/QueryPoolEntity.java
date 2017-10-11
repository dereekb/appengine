package com.dereekb.gae.utilities.collections.pool;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

/**
 * {@link QueryPool} entity that has a unique {@link ModelKey} and is typed.
 * 
 * @author dereekb
 *
 */
public interface QueryPoolEntity
        extends QueryPoolAssociate, AlwaysKeyed<ModelKey> {

	/**
	 * Returns the type of this entity.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEntityType();

}
