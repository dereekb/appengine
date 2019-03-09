package com.dereekb.gae.server.datastore.objectify.query;

import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * Objectify filter that is used for {@link SimpleQuery} values, and returns
 * another {@link SimpleQuery} result.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifySimpleQueryFilter<T>{

	public SimpleQuery<T> filter(SimpleQuery<T> query);

}
