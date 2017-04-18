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
 * @see KeyDeleter
 */
public interface Setter<T>
        extends ForceStorer<T>, Saver<T>, Deleter<T> {}
