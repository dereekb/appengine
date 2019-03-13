package com.dereekb.gae.server.datastore.objectify.helpers;

public interface CleanupPartitionDelegate<T, X> extends PartitionDelegate<T, X> {

	public X cleanup(X output);
	
}