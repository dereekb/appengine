package com.dereekb.gae.server.datastore;

/**
 * Interface for saving and deleting models.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type.
 * 
 * @see Getter
 * @see Deleter
 */
public interface Setter<T>
        extends Updater<T>, Storer<T>, ModelDeleter<T>, Deleter {}
