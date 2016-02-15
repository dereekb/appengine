package com.dereekb.gae.server.datastore.objectify.keys.generator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.utilities.misc.random.PositiveLongGenerator;
import com.dereekb.gae.utilities.misc.random.StringLongGenerator;
import com.googlecode.objectify.Key;

/**
 * {@link Generator} for Objectify {@link Key} values.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyKeyGenerator<T> extends AbstractGenerator<Key<T>> {

	private final Class<T> type;
	private final Generator<Key<T>> internalGenerator;

	public ObjectifyKeyGenerator(Class<T> type, ModelKeyType keyType) {
		this.type = type;

		if (keyType.equals(ModelKeyType.NAME)) {
			this.internalGenerator = new InternalObjectifyNameKeyGenerator();
		} else {
			this.internalGenerator = new InternalObjectifyNumberKeyGenerator();
		}
	}

	public static <T> ObjectifyKeyGenerator<T> nameKeyGenerator(Class<T> type) {
		return new ObjectifyKeyGenerator<T>(type, ModelKeyType.NAME);
	}

	public static <T> ObjectifyKeyGenerator<T> numberKeyGenerator(Class<T> type) {
		return new ObjectifyKeyGenerator<T>(type, ModelKeyType.NUMBER);
	}

	public Class<T> getType() {
		return this.type;
	}

	@Override
	public Key<T> generate(GeneratorArg arg) {
		Key<T> key = this.internalGenerator.generate(arg);
		return key;
	}

	public Set<Key<T>> generateSet(int count,
	                               GeneratorArg arg) {
		List<Key<T>> keys = this.generate(count, arg);
		return new HashSet<Key<T>>(keys);
	}

	private final class InternalObjectifyNameKeyGenerator extends AbstractGenerator<Key<T>> {

		@Override
		public Key<T> generate(GeneratorArg arg) {
			String name = StringLongGenerator.GENERATOR.generate(arg);
			Key<T> key = Key.create(ObjectifyKeyGenerator.this.type, name);
			return key;
		}

	}

	private final class InternalObjectifyNumberKeyGenerator extends AbstractGenerator<Key<T>> {

		@Override
		public Key<T> generate(GeneratorArg arg) {
			Long id = PositiveLongGenerator.GENERATOR.generate(arg);
			Key<T> key = Key.create(ObjectifyKeyGenerator.this.type, id);
			return key;
		}

	}

}
