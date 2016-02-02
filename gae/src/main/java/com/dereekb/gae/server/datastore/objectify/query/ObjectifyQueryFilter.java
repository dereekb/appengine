package com.dereekb.gae.server.datastore.objectify.query;

import com.googlecode.objectify.cmd.Query;

/**
 * Filter for an Objectify {@link Query}.
 *
 * @author dereekb
 *
 */
public interface ObjectifyQueryFilter {

	public <T> Query<T> filter(Query<T> query);

}
