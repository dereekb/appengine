package com.dereekb.gae.utilities.misc.random;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;

/**
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <C>
 *            collection type
 */
public abstract class AbstractCollectionGenerator<T, C extends Collection<T>> extends AbstractGenerator<C> {

	private Generator<T> generator;
	private IntegerGenerator countGenerator;

	public AbstractCollectionGenerator(Generator<T> generator) {
		this(generator, 1, 5);
	}
	
	public AbstractCollectionGenerator(Generator<T> generator, Integer min, Integer bound) {
		this(generator, new IntegerGenerator(min, bound));
	}
	
	public AbstractCollectionGenerator(Generator<T> generator, IntegerGenerator countGenerator) {
		super();
		this.setGenerator(generator);
		this.setCountGenerator(countGenerator);
	}

	public Generator<T> getGenerator() {
		return this.generator;
	}

	public void setGenerator(Generator<T> generator) {
		if (generator == null) {
			throw new IllegalArgumentException("generator cannot be null.");
		}

		this.generator = generator;
	}

	public void setMin(Integer least) {
		this.countGenerator.setMin(least);
	}

	public void setBound(Integer bound) {
		this.countGenerator.setBound(bound);
	}

	public IntegerGenerator getCountGenerator() {
		return this.countGenerator;
	}

	public void setCountGenerator(IntegerGenerator countGenerator) {
		if (countGenerator == null) {
			throw new IllegalArgumentException("countGenerator cannot be null.");
		}

		this.countGenerator = countGenerator;
	}

	// MARK: AbstractGenerator
	@Override
	public C generate(GeneratorArg arg) {
		C collection = this.makeNewCollection();
		Integer count = this.countGenerator.generate(arg);
		List<T> values = this.generator.generate(count, arg);
		collection.addAll(values);
		return collection;
	}

	protected abstract C makeNewCollection();

}
