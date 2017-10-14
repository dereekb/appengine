package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.utilities.query.builder.parameters.impl.AbstractQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeySetQueryFieldParameterBuilder;
import com.googlecode.objectify.Key;

/**
 * {@link ModelKeySetQueryFieldParameterBuilder} extension for
 * {@link ObjectifyKeySetFieldParameter} instances.
 * <p>
 * The primary difference is the
 * {@link #configure(ObjectifyQueryRequestLimitedBuilder, ModelKeySetQueryFieldParameter)}
 * Implementation, and when queries are configured they compare {@link Key}
 * values.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyKeySetFieldParameterBuilder<T extends ObjectifyModel<T>> extends ModelKeySetQueryFieldParameterBuilder {

	private ExtendedObjectifyModelKeyUtil<T> util;

	protected ObjectifyKeySetFieldParameterBuilder(ModelKeyType keyType, Class<T> type)
	        throws IllegalArgumentException {
		super(keyType);
		this.setType(type);
	}

	public static <T extends ObjectifyModel<T>> ObjectifyKeySetFieldParameterBuilder<T> make(ModelKeyType keyType,
	                                                                                         Class<T> type)
	        throws IllegalArgumentException {
		return new ObjectifyKeySetFieldParameterBuilder<T>(keyType, type);
	}

	public ExtendedObjectifyModelKeyUtil<T> getUtil() {
		return this.util;
	}

	public Class<T> getType() {
		return this.util.getType();
	}

	public void setType(Class<T> type) throws IllegalArgumentException {
		if (type == null) {
			throw new IllegalArgumentException("Type cannot be null.");
		}

		this.util = ExtendedObjectifyModelKeyUtil.make(type, this.getKeyType());
	}

	public ObjectifyKeySetFieldParameter make(String field,
	                                          Key<T> value)
	        throws IllegalArgumentException {
		return new ObjectifyKeySetFieldParameter(field, value);
	}

	public ObjectifyKeySetFieldParameter makeWithObjectifyKeys(String field,
	                                                           Collection<Key<T>> value)
	        throws IllegalArgumentException {
		return new ObjectifyKeySetFieldParameter(field, value);
	}

	public ObjectifyKeySetFieldParameter make(ModelKeySetQueryFieldParameter parameter)
	        throws IllegalArgumentException {
		return new ObjectifyKeySetFieldParameter(parameter);
	}

	@Override
	public ObjectifyKeySetFieldParameter make(String field,
	                                          ModelKeySetQueryFieldParameter parameter)
	        throws IllegalArgumentException {
		return new ObjectifyKeySetFieldParameter(field, parameter);
	}

	public ObjectifyKeySetFieldParameter makeObjectifyKeyParameter(String field,
	                                                               String parameterString)
	        throws IllegalArgumentException {
		return new ObjectifyKeySetFieldParameter(field, parameterString);
	}

	/**
	 * {@link AbstractQueryFieldParameter} for {@link Key} values.
	 *
	 * @author dereekb
	 *
	 */
	public final class ObjectifyKeySetFieldParameter extends ModelKeySetQueryFieldParameter
	        implements ObjectifyQueryRequestLimitedConfigurer {

		protected ObjectifyKeySetFieldParameter(AbstractQueryFieldParameter<Set<ModelKey>> parameter)
		        throws IllegalArgumentException {
			super(parameter);
		}

		protected ObjectifyKeySetFieldParameter(String field, AbstractQueryFieldParameter<Set<ModelKey>> parameter)
		        throws IllegalArgumentException {
			super(field, parameter);
		}

		protected ObjectifyKeySetFieldParameter(String field, String parameterString) throws IllegalArgumentException {
			super(field, parameterString);
		}

		protected ObjectifyKeySetFieldParameter(String field, ModelKey value) throws IllegalArgumentException {
			super(field, value);
		}

		protected ObjectifyKeySetFieldParameter(String field, Key<T> value) throws IllegalArgumentException {
			this.setField(field);
			this.setValue(value);
		}

		protected ObjectifyKeySetFieldParameter(String field, Collection<Key<T>> value)
		        throws IllegalArgumentException {
			this.setField(field);
			this.setKeyValues(value);
		}

		// MARK: Key Values
		public List<Key<T>> getObjectifyKeyValues() {
			return ObjectifyKeySetFieldParameterBuilder.this.util.convertFrom(this.getValue());
		}

		public AbstractQueryFieldParameter<Set<ModelKey>> setValue(Key<T> value) {
			if (value == null) {
				throw new IllegalArgumentException("Key cannot be null.");
			}

			return super.setSingleValue(ObjectifyKeySetFieldParameterBuilder.this.util.toModelKey(value));
		}

		public AbstractQueryFieldParameter<Set<ModelKey>> setKeyValues(Collection<Key<T>> value)
		        throws IllegalArgumentException {
			List<ModelKey> keys = null;

			if (value != null) {
				keys = ObjectifyKeySetFieldParameterBuilder.this.util.convertTo(value);
			}

			return this.setValue(keys);
		}

		// MARK: ObjectifyQueryRequestLimitedConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			List<Key<T>> values = this.getObjectifyKeyValues();
			ObjectifyAbstractQueryFieldParameter.configure(request, this, values);
		}

	}

	/**
	 * Configures the request with the input parameter converted to a
	 * {@link Key}.
	 * 
	 * If the parameter is null, nothing happens.
	 * 
	 * @param request
	 *            {@link ObjectifyQueryRequestLimitedBuilder}. Never
	 *            {@code null}.
	 * @param parameter
	 *            {@link ModelKeySetQueryFieldParameter}. May be {@code null}.
	 */
	public void configure(ObjectifyQueryRequestLimitedBuilder request,
	                      ModelKeySetQueryFieldParameter parameter) {
		if (parameter != null) {
			Set<ModelKey> modelKeys = parameter.getValue();
			List<Key<T>> key = this.util.convertFrom(modelKeys);
			ObjectifyAbstractQueryFieldParameter.configure(request, parameter, key);
		}
	}

}
