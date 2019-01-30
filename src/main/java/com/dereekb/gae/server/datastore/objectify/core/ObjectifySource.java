package com.dereekb.gae.server.datastore.objectify.core;

import com.googlecode.objectify.Objectify;

/**
 * Source for a {@link Objectify} instance.
 * 
 * @author dereekb
 *
 */
public interface ObjectifySource {

	public Objectify ofy();

}
