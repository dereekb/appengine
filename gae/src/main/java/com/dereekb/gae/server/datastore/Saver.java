package com.dereekb.gae.server.datastore;

/**
 * Interface for saving. Implements both {@link Updater} and {@link Storer}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface Saver<T>
        extends Updater<T>, Storer<T> {}
