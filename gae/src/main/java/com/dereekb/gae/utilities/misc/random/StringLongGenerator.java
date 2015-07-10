package com.dereekb.gae.utilities.misc.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;

/**
 * {@link Generator} for {@link String} values made of random {@link Long}
 * values.
 *
 * @author dereekb
 *
 */
public class StringLongGenerator extends AbstractGenerator<String> {

	public static final StringLongGenerator GENERATOR = new StringLongGenerator();

	@Override
	public String generate() {
		return randomString();
	}

	@Override
	public String generate(Long seed) {
		if (seed != null) {
			Random random = new Random(seed);
			Long value = random.nextLong();
			return value.toString();
		} else {
			return randomString();
		}
	}

	@Override
	public List<String> generate(int count,
	                             Long seed) {
		Random random = new Random(seed);
		List<String> values = new ArrayList<String>();

		for (int i = 0; i < count; i += 1) {
			Long value = random.nextLong();
			values.add(value.toString());
		}

		return values;
	}

	public static String randomString() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		Long value = random.nextLong(Long.MAX_VALUE);
		return value.toString();
	}

}
