package com.dereekb.gae.server.datastore.utility;

import com.dereekb.gae.server.datastore.Setter;

/**
 * Setter that is already configured perform changes synchronously or
 * asynchronously.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @deprecated Setters already provide pre-configured results.
 */
@Deprecated
public interface ConfiguredSetter<T>
        extends ConfiguredModelDeleter<T>, Setter<T> {

}
