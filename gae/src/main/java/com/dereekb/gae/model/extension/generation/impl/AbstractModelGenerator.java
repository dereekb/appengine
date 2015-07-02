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

	public AbstractModelGenerator() {}

	public AbstractModelGenerator(Generator<ModelKey> keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	public Generator<ModelKey> getKeyGenerator() {
		return this.keyGenerator;
	}

	public void setKeyGenerator(Generator<ModelKey> keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	protected ModelKey generateKey() {
		ModelKey key = null;

		if (this.keyGenerator != null) {
			key = this.keyGenerator.generate();
		}

		return key;
	}

	@Override
    public T generate() {
		return this.generateModel();
	}

	public T generateModel() {
		ModelKey key = this.generateKey();
		return this.generateModel(key);
	}

	// ModelGenerator
	@Override
	public abstract T generateModel(ModelKey key);

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
