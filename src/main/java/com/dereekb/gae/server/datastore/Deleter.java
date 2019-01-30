package com.dereekb.gae.server.datastore;

/**
 * Deleter that implements both {@link ModelDeleter} and {@link KeyDeleter}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface Deleter<T>
        extends ModelDeleter<T>, KeyDeleter {}
