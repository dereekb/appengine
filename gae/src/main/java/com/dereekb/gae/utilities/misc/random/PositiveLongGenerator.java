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
		return this.nextPositiveLong(random);
	}

	@Override
	public List<Long> generate(int count,
	                           GeneratorArg arg) {
		List<Long> values = new ArrayList<Long>();
		Random random = GeneratorArgImpl.getArgRandom(arg);

		for (int i = 0; i < count; i += 1) {
			Long value = this.nextPositiveLong(random);
			values.add(value);
		}

		return values;
	}

	private Long nextPositiveLong(Random random) {
		Long value = random.nextLong();

		if (value < 0) {
			value = value * -1L;
		}

		return value;
	}

	public static Long randomPositiveLong(Integer min,
	                                      Integer max) {
		return randomPositiveLong(min.longValue(), max.longValue());
	}

	public static Long randomPositiveLong(Long min,
	                                      Long max) {
		return (randomPositiveLong() + min) % max;
	}

	public static Long randomPositiveLong() {
		return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
	}

	/**
	 * @deprecated Use {@link IntegerGenerator#randomInteger(Integer)}
	 * @return
	 */
	@Deprecated
	public static Integer randomPositiveInt() {
		return ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
	}

	@Override
	public String toString() {
		return "PositiveLongGenerator []";
	}

}
