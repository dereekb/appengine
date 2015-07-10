package com.dereekb.gae.model.extension.generation.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.ModelGenerator;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ModelGenerator} implementation.
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
		return this.generateKey(null);
	}

	protected ModelKey generateKey(Long seed) {
		return this.keyGenerator.generate(seed);
	}

	protected List<ModelKey> generateKeys(int count,
	                                      Long seed) {
		return this.keyGenerator.generate(count, seed);
	}

	// MARK: Generation
	@Override
    public T generate() {
		return this.generateModel();
	}

	/**
	 * By default will call {@link #generateModel(ModelKey)} using the seed as a
	 * key, unless seed is null.
	 */
	@Override
	public T generate(Long seed) {
		ModelKey key = this.generateKey(seed);
		return this.generateModel(key, seed);
	}

	@Override
	public List<T> generate(int count,
	                        Long seed) {
		List<ModelKey> keys = this.generateKeys(count, seed);
		return this.generateModels(keys);
	}

	// ModelGenerator
	public T generateModel() {
		ModelKey key = this.generateKey();
		return this.generateModel(key);
	}

	@Override
	public T generateModel(ModelKey key) {
		Long seed = null;

		if (key != null) {
			Integer hash = key.hashCode();
			seed = hash.longValue();
		}

		return this.generateModel(key, seed);
	}

	public abstract T generateModel(ModelKey key,
	                                Long seed);

	@Override
	public List<T> generateModels(Iterable<ModelKey> keys) {
		List<T> models = new ArrayList<T>();

		for (ModelKey key : keys) {
			T model = this.generateModel(key);
			models.add(model);
		}

		return models;
	}

}
