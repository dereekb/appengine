package com.dereekb.gae.server.datastore.objectify.query;

import com.googlecode.objectify.cmd.SimpleQuery;

public interface ObjectifyKeyRequestFilter<T>{
	
	public SimpleQuery<T> filter(SimpleQuery<T> query);

}
