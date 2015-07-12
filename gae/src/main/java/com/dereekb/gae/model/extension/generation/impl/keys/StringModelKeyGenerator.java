package com.dereekb.gae.model.extension.generation.impl.keys;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.random.StringLongGenerator;

/**
 * Used for generating {@link ModelKey} instances with {@link String} id values.
 *
 * Uses {@link StringLongGenerator#GENERATOR} to generate random values.
 *
 * @author dereekb
 */
public class StringModelKeyGenerator extends AbstractGenerator<ModelKey> {

	public static final StringModelKeyGenerator GENERATOR = new StringModelKeyGenerator();

	@Override
	public ModelKey generate() {
		String key = StringLongGenerator.GENERATOR.generate();
		return new ModelKey(key);
	}

	@Override
	public ModelKey generate(GeneratorArg arg) {
		String key = StringLongGenerator.GENERATOR.generate(arg);
		return new ModelKey(key);
	}

}
