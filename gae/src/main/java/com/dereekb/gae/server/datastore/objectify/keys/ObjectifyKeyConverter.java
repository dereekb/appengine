package com.dereekb.gae.server.datastore.objectify.keys;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.googlecode.objectify.Key;

/**
 * Used to convert {@link Key} instances to {@link ModelKey}.
 *
 * @author dereekb
 *
 * @param <T>
 *            {@link Key}'s generic type.
 * @param <K>
 *            Conversion type
 */
public interface ObjectifyKeyConverter<T, K>
        extends ObjectifyKeyReader<T, K>, ObjectifyKeyWriter<T, K> {

}
