package com.dereekb.gae.utilities.misc.random;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;

/**
 * {@link Generator} for {@link Boolean} that has a chance value associated with
 * it for generation.
 *
 * @author dereekb
 *
 */
public class ChanceBooleanGenerator extends AbstractGenerator<Boolean> {

	private static final Integer MIN_CHANCE = 0;
	private static final Integer MAX_CHANCE = 100;

	/**
	 * The base chance. The rolled value must be less than or equal to the
	 * chance to succeed.
	 *
	 * Max value of 100.
	 */
	private Integer chance;

	public ChanceBooleanGenerator(Integer chance) {
		this.setChance(chance);
	}

	public Integer getChance() {
		return this.chance;
	}

	public void setChance(Integer chance) throws IllegalArgumentException {

		if (chance == null) {
			throw new IllegalArgumentException("Chance cannot be null.");
		}

		if (chance > MAX_CHANCE || chance < MIN_CHANCE) {
			throw new IllegalArgumentException("Chance must be atleast 0 and less than or equal to 100.");
		}

		this.chance = chance;
	}

	@Override
	public Boolean generate(GeneratorArg arg) {
		Integer roll = arg.getGeneratorRandom().nextInt(MAX_CHANCE);
		return roll <= this.chance;
	}

}
