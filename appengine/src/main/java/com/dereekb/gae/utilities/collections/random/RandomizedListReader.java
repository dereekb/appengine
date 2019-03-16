package com.dereekb.gae.utilities.collections.random;

import java.util.List;
import java.util.Random;

/**
 * Used for retrieving random elements from a list.
 *
 * @author dereekb
 */
public final class RandomizedListReader {

	private final Random random;

	public RandomizedListReader() {
		this(new Random());
	}

	public RandomizedListReader(int seed) {
		this(new Random(seed));
	}

	public RandomizedListReader(Random random) {
		this.random = random;
	}

	public Random getRandom() {
		return this.random;
	}

	public <T> T getRandomElement(List<T> elements) {
		int index = this.random.nextInt(elements.size());
		return elements.get(index);
	}

}
