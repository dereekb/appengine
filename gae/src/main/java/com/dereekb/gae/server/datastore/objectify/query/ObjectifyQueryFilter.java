package com.dereekb.gae.server.datastore.objectify.query;

import com.googlecode.objectify.cmd.Query;

public interface ObjectifyQueryFilter {
	
	public <T> Query<T> filter(Query<T> query);
	
}
