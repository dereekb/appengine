package com.dereekb.gae.server.datastore;

/**
 * {@link Setter} extension for {@link ForceStorer}.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type.
 * 
 */
public interface ForceSetter<T>
        extends Setter<T>, ForceStorer<T> {}
