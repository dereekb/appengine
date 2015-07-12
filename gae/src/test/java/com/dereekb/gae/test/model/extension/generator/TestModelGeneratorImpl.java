package com.dereekb.gae.test.model.extension.generator;

import java.util.List;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.ModelGenerator;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link TestModelGenerator} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class TestModelGeneratorImpl<T extends UniqueModel>
        implements TestModelGenerator<T> {

	protected Setter<T> setter;
	protected ModelGenerator<T> generator;

	public TestModelGeneratorImpl() {}

	public TestModelGeneratorImpl(Setter<T> setter, ModelGenerator<T> generator) {
		this.setter = setter;
		this.generator = generator;
	}

	@Override
    public T generate() {
		T model = this.generator.generate();
		this.setter.save(model, false);
		return model;
    }

	@Override
	public T generate(GeneratorArg arg) {
		T model = this.generator.generate(arg);
		this.setter.save(model, false);
		return model;
	}

	@Override
	public List<T> generate(int count) {
		return this.generate(count, null);
	}

	@Override
	public List<T> generate(int count,
	                        GeneratorArg arg) {
		List<T> models = this.generator.generate(count, arg);
		this.setter.save(models, false);
		return models;
    }

	@Override
	public T generateModel(ModelKey identifier) {
		T model = this.generator.generateModel(identifier);
		this.setter.save(model, false);
		return model;
	}

	@Override
	public List<T> generateModels(Iterable<ModelKey> identifiers) {
		List<T> models = this.generator.generateModels(identifiers);
		this.setter.save(models, false);
		return models;
	}

}
