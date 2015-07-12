package com.dereekb.gae.model.extension.generation;

import java.util.Random;

/**
 * Represents {@link Generator} arguments.
 *
 * @author dereekb
 */
public interface GeneratorArg {

	/**
	 * Returns the current seed value, if available.
	 *
	 * @return Current seed value, or {@code null} if not available.
	 */
	public Long getGeneratorSeed();

	/**
	 * Returns the current {@link Random} value.
	 *
	 * @return Returns the current {@link Random} value. Never {@code null}.
	 */
	public Random getGeneratorRandom();

}
