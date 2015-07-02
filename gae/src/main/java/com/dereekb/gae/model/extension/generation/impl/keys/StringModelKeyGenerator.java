package com.dereekb.gae.model.extension.generation.impl.keys;

import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for generating {@link ModelKey} instances with {@link String} id values.
 *
 * @author dereekb
 */
public class StringModelKeyGenerator extends AbstractGenerator<ModelKey> {

	@Override
	public ModelKey generate() {
		Long value = this.randomPositiveLong();
		String key = value.toString();
		return new ModelKey(key);
	}

}
