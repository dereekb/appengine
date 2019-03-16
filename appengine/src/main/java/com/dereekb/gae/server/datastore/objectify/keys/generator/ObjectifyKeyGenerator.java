package com.dereekb.gae.server.datastore.objectify.keys.generator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.utilities.cache.map.CacheMap;
import com.dereekb.gae.utilities.cache.map.CacheMapDelegate;
import com.dereekb.gae.utilities.cache.map.impl.CacheMapImpl;
import com.dereekb.gae.utilities.misc.random.PositiveLongGenerator;
import com.dereekb.gae.utilities.misc.random.StringLongGenerator;
import com.googlecode.objectify.Key;

/**
 * {@link Generator} for Objectify {@link Key} values.
 * 
 * Use {@link #nameKeyGenerator(Class)} and {@link #numberKeyGenerator(Class)}
 * for generators.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class ObjectifyKeyGenerator<T> extends AbstractGenerator<Key<T>> {

	protected final Class<T> type;

	@SuppressWarnings("unchecked")
	public static <T> ObjectifyKeyGenerator<T> nameKeyGenerator(Class<T> type) {
		return (ObjectifyKeyGenerator<T>) ObjectifyNameKeyGenerator.SINGLETON_CACHE.get(type);
	}

	@SuppressWarnings("unchecked")
	public static <T> ObjectifyKeyGenerator<T> numberKeyGenerator(Class<T> type) {
		return (ObjectifyKeyGenerator<T>) ObjectifyLongKeyGenerator.SINGLETON_CACHE.get(type);
	}

	private ObjectifyKeyGenerator(Class<T> type) {
		this.type = type;
	}

	public Class<T> getType() {
		return this.type;
	}

	@Override
	public abstract Key<T> generate(GeneratorArg arg);

	public Set<Key<T>> generateSet(int count,
	                               GeneratorArg arg) {
		List<Key<T>> keys = this.generate(count, arg);
		return new HashSet<Key<T>>(keys);
	}

	private static final class ObjectifyNameKeyGenerator<T> extends ObjectifyKeyGenerator<T> {

		private static final CacheMap<Class<?>, ObjectifyNameKeyGenerator<?>> SINGLETON_CACHE = new CacheMapImpl<Class<?>, ObjectifyNameKeyGenerator<?>>(
		        new CacheMapDelegate<Class<?>, ObjectifyNameKeyGenerator<?>>() {

			        @SuppressWarnings({ "unchecked", "rawtypes" })
			        @Override
			        public ObjectifyNameKeyGenerator<?> makeCacheElement(Class<?> key) throws IllegalArgumentException {
				        return new ObjectifyNameKeyGenerator(key);
			        }

		        });

		private ObjectifyNameKeyGenerator(Class<T> type) {
			super(type);
		}

		@Override
		public Key<T> generate(GeneratorArg arg) {
			String name = StringLongGenerator.GENERATOR.generate(arg);
			Key<T> key = Key.create(this.type, name);
			return key;
		}

	}

	private static final class ObjectifyLongKeyGenerator<T> extends ObjectifyKeyGenerator<T> {

		private static final CacheMap<Class<?>, ObjectifyLongKeyGenerator<?>> SINGLETON_CACHE = new CacheMapImpl<Class<?>, ObjectifyLongKeyGenerator<?>>(
		        new CacheMapDelegate<Class<?>, ObjectifyLongKeyGenerator<?>>() {

			        @SuppressWarnings({ "unchecked", "rawtypes" })
			        @Override
			        public ObjectifyLongKeyGenerator<?> makeCacheElement(Class<?> key) throws IllegalArgumentException {
				        return new ObjectifyLongKeyGenerator(key);
			        }

		        });

		private ObjectifyLongKeyGenerator(Class<T> type) {
			super(type);
		}

		@Override
		public Key<T> generate(GeneratorArg arg) {
			Long id = PositiveLongGenerator.GENERATOR.generate(arg);
			Key<T> key = Key.create(this.type, id);
			return key;
		}

	}

}
