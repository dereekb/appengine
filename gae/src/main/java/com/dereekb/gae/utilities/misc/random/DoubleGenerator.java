package com.dereekb.gae.utilities.misc.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;

/**
 * {@link Generator} implementaiton for {@link Double}.
 *
 * Generates {@link Double} values between the {@link #least} and {@link #bound}
 * values set.
 *
 * @author dereekb
 *
 */
public class DoubleGenerator extends AbstractGenerator<Double> {

	private static final Double DEFAULT_LEAST_VALUE = 0.0d;
	private static final Double DEFAULT_BOUND_VALUE = 1.0d;

	/**
	 * Lower bound value for generated {@link Double} values.
	 */
	private Double least = DEFAULT_LEAST_VALUE;

	/**
	 * Upper bound value for generated {@link Double} values.
	 */
	private Double bound = DEFAULT_BOUND_VALUE;

	public DoubleGenerator(Double least, Double bound) {
		this.least = least;
		this.bound = bound;
	}

	public Double getLeast() {
		return this.least;
	}

	public void setLeast(Double least) {
		if (least == null) {
			least = 0.0d;
		}

		this.least = least;
	}

	public Double getBound() {
		return this.bound;
	}

	public void setBound(Double bound) {
		if (bound == null) {
			bound = 1.0d;
		}

		this.bound = bound;
	}

	@Override
	public Double generate() {
		return randomDouble(this.least, this.bound);
	}

	@Override
	public Double generate(Long seed) {
		Random random = new Random(seed);
		return random.nextDouble() * (this.bound - this.least) + this.least;
	}

	@Override
	public List<Double> generate(int count,
	                             Long seed) {
		Random random = new Random(seed);
		List<Double> values = new ArrayList<Double>();

		for (int i = 0; i < count; i += 1) {
			Double value = random.nextDouble();
			values.add(value);
		}

		return values;
	}

	public static Double randomDouble() {
		return ThreadLocalRandom.current().nextDouble();
	}

	public static Double randomDouble(Double max) {
		return ThreadLocalRandom.current().nextDouble(max);
	}

	public static Double randomDouble(Double least,
	                                  Double bound) {
		return ThreadLocalRandom.current().nextDouble(least, bound);
	}

	@Override
	public String toString() {
		return "DoubleGenerator [least=" + this.least + ", bound=" + this.bound + "]";
	}

}
