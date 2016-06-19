package com.dereekb.gae.model.extension.generation.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.ModelGenerator;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract {@link ModelGenerator} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 */
public abstract class AbstractModelGenerator<T extends UniqueModel> extends AbstractGenerator<T>
        implements ModelGenerator<T> {

	private Generator<ModelKey> keyGenerator;

	public AbstractModelGenerator(Generator<ModelKey> keyGenerator) {
		this.setKeyGenerator(keyGenerator);
	}

	public Generator<ModelKey> getKeyGenerator() {
		return this.keyGenerator;
	}

	public void setKeyGenerator(Generator<ModelKey> keyGenerator) throws IllegalArgumentException {
		if (keyGenerator == null) {
			throw new IllegalArgumentException("Key Generator cannot be null.");
		}

		this.keyGenerator = keyGenerator;
	}

	// MARK: Key Generation
	protected ModelKey generateKey() {
		return this.keyGenerator.generate();
	}

	protected ModelKey generateKey(GeneratorArg arg) {
		return this.keyGenerator.generate(arg);
	}

	protected List<ModelKey> generateKeys(int count,
	                                      GeneratorArg arg) {
		return this.keyGenerator.generate(count, arg);
	}

	// MARK: Generation
	@Override
    public T generate() {
		return this.generateModel();
	}

	@Override
	public T generate(GeneratorArg arg) {
		ModelKey key = this.generateKey(arg);
		return this.generateModel(key, arg);
	}

	@Override
	public List<T> generate(int count,
	                        GeneratorArg arg) {
		List<ModelKey> keys = this.generateKeys(count, arg);
		return this.generateModels(keys);
	}

	// ModelGenerator
	public T generateModel() {
		ModelKey key = this.generateKey();
		return this.generateModel(key);
	}

	@Override
	public T generateModelWithoutKey() {
		GeneratorArg arg = new GeneratorArgImpl();
		return this.generateModel(null, arg);
	}

	@Override
	public T generateModel(ModelKey key) {
		GeneratorArg arg = null;

		if (key != null) {
			Integer hash = key.hashCode();
			arg = new GeneratorArgImpl(hash.longValue());
		} else {
			arg = new GeneratorArgImpl();
		}

		return this.generateModel(key, arg);
	}

	/**
	 * Generates a new model.
	 *
	 * @param key
	 *            (Optional) {@link ModelKey}.
	 * @param arg
	 *            {@link GeneratorArg} to use for generation. Never {@code null}
	 *            .
	 * @return new model instance.
	 */
	protected abstract T generateModel(ModelKey key,
	                                   GeneratorArg arg);

	@Override
	public List<T> generateModels(Iterable<ModelKey> keys) {
		List<T> models = new ArrayList<T>();
		GeneratorArg arg = new GeneratorArgImpl();

		for (ModelKey key : keys) {
			T model = this.generateModel(key, arg);
			models.add(model);
		}

		return models;
	}

}
