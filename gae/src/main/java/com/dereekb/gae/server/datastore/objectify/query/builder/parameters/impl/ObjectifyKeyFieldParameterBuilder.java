package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.utilities.query.builder.parameters.impl.AbstractQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder;
import com.googlecode.objectify.Key;

/**
 * Builder for {@link ObjectifyKeyFieldParameter} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyKeyFieldParameterBuilder<T extends ObjectifyModel<T>> extends ModelKeyQueryFieldParameterBuilder {

	private ExtendedObjectifyModelKeyUtil<T> util;

	public ObjectifyKeyFieldParameterBuilder(ModelKeyType keyType, Class<T> type) throws IllegalArgumentException {
		super(keyType);
		this.setType(type);
	}

	public static <T extends ObjectifyModel<T>> ObjectifyKeyFieldParameterBuilder<T> builder(ModelKeyType keyType,
	                                                                                         Class<T> type)
	        throws IllegalArgumentException {
		return new ObjectifyKeyFieldParameterBuilder<T>(keyType, type);
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

		this.util = new ExtendedObjectifyModelKeyUtil<T>(type, this.getKeyType());
	}

	public ObjectifyKeyFieldParameter make(String field,
	                                       Key<T> value)
	        throws IllegalArgumentException {
		return new ObjectifyKeyFieldParameter(field, value);
	}

	public ObjectifyKeyFieldParameter make(ModelKeyQueryFieldParameter parameter) throws IllegalArgumentException {
		return new ObjectifyKeyFieldParameter(parameter);
	}

	public ObjectifyKeyFieldParameter make(String field,
	                                       ModelKeyQueryFieldParameter parameter)
	        throws IllegalArgumentException {
		return new ObjectifyKeyFieldParameter(field, parameter);
	}

	public ObjectifyKeyFieldParameter makeObjectifyKeyParameter(String field,
	                                                            String parameterString)
	        throws IllegalArgumentException {
		return new ObjectifyKeyFieldParameter(field, parameterString);
	}

	/**
	 * {@link AbstractQueryFieldParameter} for {@link Key} values.
	 *
	 * @author dereekb
	 *
	 */
	public final class ObjectifyKeyFieldParameter extends ModelKeyQueryFieldParameter
	        implements ObjectifyQueryRequestLimitedConfigurer {

		protected ObjectifyKeyFieldParameter(AbstractQueryFieldParameter<ModelKey> parameter)
		        throws IllegalArgumentException {
			super(parameter);
		}

		protected ObjectifyKeyFieldParameter(String field, AbstractQueryFieldParameter<ModelKey> parameter)
		        throws IllegalArgumentException {
			super(field, parameter);
		}

		protected ObjectifyKeyFieldParameter(String field, String parameterString) throws IllegalArgumentException {
			super(field, parameterString);
		}

		protected ObjectifyKeyFieldParameter(String field, ModelKey value) throws IllegalArgumentException {
			super(field, value);
		}

		protected ObjectifyKeyFieldParameter(String field, Key<T> value) throws IllegalArgumentException {
			this.setField(field);
			this.setValue(value);
		}

		// MARK: Key Values
		public Key<T> getObjectifyKeyValue() {
			Key<T> key = null;

			if (this.getValue() != null) {
				key = ObjectifyKeyFieldParameterBuilder.this.util.fromModelKey(this.getValue());
			}

			return key;
		}

		public AbstractQueryFieldParameter<ModelKey> setValue(Key<T> value) throws IllegalArgumentException {
			ModelKey key = null;

			if (value != null) {
				key = ObjectifyKeyFieldParameterBuilder.this.util.readKey(value);
			}

			return this.setValue(key);
		}

		// MARK: ObjectifyQueryRequestLimitedConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			Key<T> value = this.getObjectifyKeyValue();
			ObjectifyAbstractQueryFieldParameter.configure(request, this, value);
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
	 *            {@link ModelKeyQueryFieldParameter}. May be {@code null}.
	 */
	public void configure(ObjectifyQueryRequestLimitedBuilder request,
	                      ModelKeyQueryFieldParameter parameter) {
		if (parameter != null) {
			ModelKey modelKey = parameter.getValue();
			Key<T> key = this.util.fromModelKey(modelKey);
			ObjectifyAbstractQueryFieldParameter.configure(request, parameter, key);
		}
	}

}
