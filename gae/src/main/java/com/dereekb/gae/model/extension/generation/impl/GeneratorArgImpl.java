package com.dereekb.gae.model.extension.generation.impl;

import java.util.Random;

import com.dereekb.gae.model.extension.generation.GeneratorArg;

/**
 * {@link GeneratorArg} implementation.
 *
 * @author dereekb
 *
 */
public class GeneratorArgImpl
        implements GeneratorArg {

	private Long seed;
	private Random random;

	public GeneratorArgImpl() {
		this.setRandom(null);
	}

	public GeneratorArgImpl(Long seed) {
		this.setSeed(seed);
	}

	public GeneratorArgImpl(Random random) {
		this.setRandom(random);
	}

	public Long getSeed() {
		return this.seed;
	}

	/**
	 * Sets the seed to the input value, and updates {@link #random}.
	 *
	 * @param seed
	 *            New seed. Can be {@code null}.
	 */
	public void setSeed(Long seed) {
		this.seed = seed;
		this.generateRandom(seed);
	}

	public Random getRandom() {
		return this.random;
	}

	/**
	 * Sets a new {@link Random} value. If the input value is null, will
	 * generate a new {@link Random} using {@link #seed}. If the input random is
	 * not null, will clear the current {@link #seed} value.
	 *
	 * @param random
	 *            New random. Can be {@code null}.
	 */
	public void setRandom(Random random) {
		if (random == null) {
			random = this.generateRandom(this.seed);
		} else {
			this.seed = null;
		}

		this.random = random;
	}

	protected Random generateRandom(Long seed) {
		return new Random(seed);
	}

	@Override
	public Long getGeneratorSeed() {
		return this.seed;
	}

	@Override
	public Random getGeneratorRandom() {
		return this.random;
	}

	@Override
	public String toString() {
		return "GeneratorArgImpl [seed=" + this.seed + ", random=" + this.random + "]";
	}

	/**
	 * Returns a {@link Random} instance from the input arg if not {@code null}.
	 * If {@code null}, will return a new {@link Random} instance.
	 *
	 * @param arg
	 * @return
	 */
	public static Random getArgRandom(GeneratorArg arg) {
		Random random = null;

		if (arg != null) {
			random = arg.getGeneratorRandom();
		}

		return random;
	}

}
