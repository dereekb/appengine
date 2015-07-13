package com.dereekb.gae.utilities.misc.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.extension.generation.impl.GeneratorArgImpl;

/**
 * {@link Generator} implementaiton for {@link Integer}.
 *
 * Generates {@link Integer} values between the {@link #min} and {@link #bound}
 * values set.
 *
 * @author dereekb
 *
 */
public class IntegerGenerator extends AbstractGenerator<Integer> {

	private static final Integer DEFAULT_MIN_VALUE = Integer.MIN_VALUE;
	private static final Integer DEFAULT_BOUND_VALUE = Integer.MAX_VALUE;

	/**
	 * Lower bound value for generated {@link Integer} values.
	 */
	private Integer min = DEFAULT_MIN_VALUE;

	/**
	 * Upper bound value for generated {@link Integer} values.
	 */
	private Integer bound = DEFAULT_BOUND_VALUE;

	public IntegerGenerator() {}

	public IntegerGenerator(Integer min, Integer bound) {
		this.min = min;
		this.bound = bound;
	}

	public Integer getMin() {
		return this.min;
	}

	public void setMin(Integer least) {
		if (least == null) {
			least = DEFAULT_MIN_VALUE;
		}

		this.min = least;
	}

	public Integer getBound() {
		return this.bound;
	}

	public void setBound(Integer bound) {
		if (bound == null) {
			bound = DEFAULT_BOUND_VALUE;
		}

		this.bound = bound;
	}

	@Override
	public Integer generate() {
		return randomInteger(this.min, this.bound);
	}

	@Override
	public Integer generate(GeneratorArg arg) {
		Random random = arg.getGeneratorRandom();
		return this.generate(random);
	}

	@Override
	public List<Integer> generate(int count,
	                              GeneratorArg arg) {
		List<Integer> values = new ArrayList<Integer>();
		Random random = GeneratorArgImpl.getArgRandom(arg);

		for (int i = 0; i < count; i += 1) {
			Integer value = this.generate(random);
			values.add(value);
		}

		return values;
	}

	private Integer generate(Random random) {
		return random.nextInt(this.bound - this.min) + this.min;
	}

	public static Integer randomInteger() {
		return ThreadLocalRandom.current().nextInt();
	}

	public static Integer randomInteger(Integer bound) {
		return ThreadLocalRandom.current().nextInt(bound);
	}

	public static Integer randomInteger(Integer least,
	                                    Integer bound) {
		return ThreadLocalRandom.current().nextInt(least, bound);
	}

	@Override
	public String toString() {
		return "IntegerGenerator [min=" + this.min + ", bound=" + this.bound + "]";
	}

}
