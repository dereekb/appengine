package com.dereekb.gae.server.auth.model.login.security.requestor.impl;

import com.dereekb.gae.server.auth.model.login.security.requestor.RequesterContextType;
import com.dereekb.gae.server.auth.model.login.security.requestor.RequesterPair;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;

/**
 * {@link RequesterPair} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            object type
 */
public class RequesterPairImpl<T> extends HandlerPair<RequesterContextType, T>
        implements RequesterPair<T> {

	public RequesterPairImpl(RequesterContextType key, T object) {
		super(key, object);
	}

	public static <T> RequesterPairImpl<T> make(RequesterContextType type, T object) {
		return new RequesterPairImpl<T>(type, object);
	}

}
