package com.dereekb.gae.utilities.misc.random;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.generation.Generator;

/**
 * {@link AbstractCollectionGenerator} for {@link List}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ListCollectionGenerator<T> extends AbstractCollectionGenerator<T, List<T>> {

	public ListCollectionGenerator(Generator<T> generator, Integer min, Integer bound) {
		super(generator, min, bound);
	}

	public ListCollectionGenerator(Generator<T> generator, IntegerGenerator countGenerator) {
		super(generator, countGenerator);
	}

	public ListCollectionGenerator(Generator<T> generator) {
		super(generator);
	}

	// MARK: AbstractCollectionGenerator
	@Override
	protected List<T> makeNewCollection() {
		return new ArrayList<T>();
	}

	@Override
	public String toString() {
		return "ListCollectionGenerator [getGenerator()=" + this.getGenerator() + ", getCountGenerator()="
		        + this.getCountGenerator() + "]";
	}

}
