package com.dereekb.gae.utilities.misc.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.extension.generation.impl.GeneratorArgImpl;

/**
 * {@link Generator} for positive {@link Long} values.
 *
 * @author dereekb
 *
 */
public class PositiveLongGenerator extends AbstractGenerator<Long> {

	public static final PositiveLongGenerator GENERATOR = new PositiveLongGenerator();

	@Override
	public Long generate() {
		return randomPositiveLong();
	}

	@Override
	public Long generate(GeneratorArg arg) {
		Random random = arg.getGeneratorRandom();
		return random.nextLong();
	}

	@Override
	public List<Long> generate(int count,
	                           GeneratorArg arg) {
		List<Long> values = new ArrayList<Long>();
		Random random = GeneratorArgImpl.getArgRandom(arg);

		for (int i = 0; i < count; i += 1) {
			Long value = random.nextLong();
			values.add(value);
		}

		return values;
	}

	public static Long randomPositiveLong() {
		return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
	}

	public static Integer randomPositiveInt() {
		return ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
	}

	@Override
	public String toString() {
		return "PositiveLongGenerator []";
	}

}
