package com.thevisitcompany.gae.test.deprecated.models.generator;

import java.util.ArrayList;
import java.util.List;

import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.model.extension.generation.ModelGenerator;
import com.thevisitcompany.gae.model.extension.generation.TypedGenerator;
import com.thevisitcompany.gae.server.datastore.Setter;

/**
 * Testing Generator that wraps a generator, and automatically saves any objects that are generated to the registry.
 * 
 * @author dereekb
 * 
 */
public abstract class TestingGenerator<T extends KeyedModel<K>, K> extends ModelGenerator<T, K>
        implements TypedGenerator<T> {

	protected Setter<T> setter;
	protected ModelGenerator<T, K> generator;

	public TestingGenerator() {}

	public TestingGenerator(ModelGenerator<T, K> generator) {
		this.generator = generator;
	}

	@Override
	public T generateModel() {
		return this.generateModel(null);
	}

	@Override
	public T generateModel(K identifier) {
		T model = this.generator.generateModel(identifier);
		setter.save(model, false);
		return model;
	}

	@Override
	public List<T> generateModelsWithIds(Iterable<K> identifiers) {
		List<T> models = this.generator.generateModelsWithIds(identifiers);
		setter.save(models, false);
		return models;
	}

	@Override
	public T generate(String type) {
		return this.generateModelWithType(type);
	}

	protected abstract T generateModelWithType(String type);

	protected abstract T generateModelWithType(K identifier,
	                                           String type);

	@Override
	public List<T> generate(int count,
	                        String type) {
		List<T> models = new ArrayList<T>();

		for (int i = 0; i < count; i += 1) {
			T model = this.generateModelWithType(type);
			models.add(model);
		}

		setter.save(models, false);
		return models;
	}

	public List<T> generate(Iterable<K> identifiers,
	                        String type) {
		List<T> models = new ArrayList<T>();

		for (K identifier : identifiers) {
			T model = this.generateModelWithType(identifier, type);
			models.add(model);
		}

		setter.save(models, false);
		return models;
	}

	@Override
	public List<T> generate(int count) {
		List<T> models = this.generator.generate(count);
		setter.save(models, false);
		return models;
	}

	public Setter<T> getSetter() {
		return setter;
	}

	public void setSetter(Setter<T> setter) {
		this.setter = setter;
	}

	public ModelGenerator<T, K> getGenerator() {
		return generator;
	}

	public void setGenerator(ModelGenerator<T, K> generator) {
		generator.setKeyGenerator(null);
		this.generator = generator;
	}

}
