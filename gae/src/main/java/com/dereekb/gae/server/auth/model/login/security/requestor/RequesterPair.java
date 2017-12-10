package com.dereekb.gae.server.auth.model.login.security.requestor;

import com.dereekb.gae.utilities.collections.pairs.Pair;

/**
 * {@link Pair} with a {@link RequesterContextType} key.
 *
 * @author dereekb
 *
 * @param <T>
 *            object type.
 */
public interface RequesterPair<T>
        extends Pair<RequesterContextType, T> {}
