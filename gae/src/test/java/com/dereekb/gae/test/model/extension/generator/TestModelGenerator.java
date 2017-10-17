package com.dereekb.gae.test.model.extension.generator;

import java.util.List;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.ModelGenerator;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for generating test models.
 *
 * @author dereekb
 *
 */
public interface TestModelGenerator<T extends UniqueModel>
        extends ModelGenerator<T> {

	/**
	 * Convenience accessor for the generator's setter.
	 */
	public Setter<T> getSetter();

	/**
	 * Generates a new model with a random identifier.
	 *
	 * The model is saved to the configured datastore.
	 *
	 * @return Generated model. Never null.
	 */
	@Override
	public T generate();

	/**
	 * Generates a new model with the input delegate.
	 * 
	 * @param delegate
	 *            {@link TestModelGeneratorDelegate}. Never {@code null}.
	 * @return Generated model. Never {@code null}.
	 */
	public T generate(TestModelGeneratorDelegate<T> delegate);

	/**
	 * Generates new models with random identifiers. The model is saved to the
	 * configured datastore.
	 *
	 * @param count
	 * @return Generated models. Never null.
	 */
	public List<T> generate(int count);

	/**
	 * Generates new models with random identifiers.
	 * <p>
	 * Instead of configuring the models internally, they are
	 *
	 * @param count
	 * @return Generated models. Never null.
	 */
	public List<T> generate(int count,
	                        TestModelGeneratorDelegate<T> delegate);

	/**
	 * Generates new models with random identifiers. The model is saved to the
	 * configured datastore. Uses a seed.
	 *
	 * @param count
	 * @param seed
	 * @return Generated models. Never null.
	 */
	@Override
	public List<T> generate(int count,
	                        GeneratorArg arg);

	/**
	 * Generates a single model with the passed identifier.
	 * <p>
	 * The model is saved to the configured datastore.
	 *
	 * @param identifier
	 * @return Generated model. Never {@code null}.
	 */
	@Override
	public T generateModel(ModelKey identifier);

	/**
	 * Generates new models with the passed identifiers.
	 *
	 * The model is saved to the configured datastore.
	 *
	 * @param identifiers
	 * @return Generated models. Never null.
	 */
	@Override
	public List<T> generateModels(Iterable<ModelKey> identifiers);

}
