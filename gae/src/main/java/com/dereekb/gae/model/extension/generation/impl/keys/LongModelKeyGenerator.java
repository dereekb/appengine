package com.dereekb.gae.model.extension.generation.impl.keys;

import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.random.PositiveLongGenerator;

/**
 * Used for generating {@link ModelKey} instances with {@link Long} id values.
 *
 * Uses {@link PositiveLongGenerator#GENERATOR} to generate random values.
 *
 * @author dereekb
 */
public final class LongModelKeyGenerator extends AbstractGenerator<ModelKey> {

	public static final LongModelKeyGenerator GENERATOR = new LongModelKeyGenerator();

	@Override
	public ModelKey generate() {
		Long key = PositiveLongGenerator.GENERATOR.generate();
		return new ModelKey(key);
	}

	@Override
	public ModelKey generate(Long seed) {
		Long key = PositiveLongGenerator.GENERATOR.generate(seed);
		return new ModelKey(key);
	}

}
