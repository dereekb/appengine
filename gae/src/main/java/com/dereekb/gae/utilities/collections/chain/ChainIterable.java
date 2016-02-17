package com.dereekb.gae.utilities.collections.chain;

import java.util.Iterator;

/**
 * Special type that implements both iterable and iterator.
 *
 * @author dereekb
 *
 * @param <T>
 *            element type
 */
public interface ChainIterable<T>
        extends Iterable<T>, Iterator<T> {

}
