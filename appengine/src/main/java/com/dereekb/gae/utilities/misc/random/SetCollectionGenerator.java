package com.dereekb.gae.utilities.misc.random;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.generation.Generator;

/**
 * {@link AbstractCollectionGenerator} for {@link Set}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class SetCollectionGenerator<T> extends AbstractCollectionGenerator<T, Set<T>> {

	public SetCollectionGenerator(Generator<T> generator, Integer min, Integer bound) {
		super(generator, min, bound);
	}

	public SetCollectionGenerator(Generator<T> generator, IntegerGenerator countGenerator) {
		super(generator, countGenerator);
	}

	public SetCollectionGenerator(Generator<T> generator) {
		super(generator);
	}

	// MARK: AbstractCollectionGenerator
	@Override
	protected Set<T> makeNewCollection() {
		return new HashSet<T>();
	}

	@Override
	public String toString() {
		return "SetCollectionGenerator [getGenerator()=" + this.getGenerator() + ", getCountGenerator()="
		        + this.getCountGenerator() + "]";
	}

}
