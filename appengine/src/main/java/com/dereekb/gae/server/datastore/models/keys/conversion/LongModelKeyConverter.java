package com.dereekb.gae.server.datastore.models.keys.conversion;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for converting a {@link Long} value to a {@link ModelKey}.
 *
 * @author dereekb
 *
 */
public interface LongModelKeyConverter
        extends BidirectionalConverter<Long, ModelKey>, DirectionalConverter<Long, ModelKey>,
        SingleDirectionalConverter<Long, ModelKey> {

}
