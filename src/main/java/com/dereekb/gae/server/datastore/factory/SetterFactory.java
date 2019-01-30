package com.dereekb.gae.server.datastore.factory;

import com.dereekb.gae.server.datastore.Setter;

public interface SetterFactory<T> {

	public Setter<T> makeSetter();

}
