package com.dereekb.gae.model.extension.generation.impl.keys;

import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for generating {@link ModelKey} instances with {@link Long} id values.
 *
 * @author dereekb
 */
public class LongModelKeyGenerator extends AbstractGenerator<ModelKey> {

	@Override
	public ModelKey generate() {
		Long key = this.randomPositiveLong();
		return new ModelKey(key);
	}

}
