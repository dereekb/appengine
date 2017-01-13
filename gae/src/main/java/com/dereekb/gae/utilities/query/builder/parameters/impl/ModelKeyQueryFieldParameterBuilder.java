package com.dereekb.gae.utilities.query.builder.parameters.impl;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * Builder for {@link ModelKeyQueryFieldParameter} instances.
 * 
 * @author dereekb
 *
 */
public class ModelKeyQueryFieldParameterBuilder {

	public static final ModelKeyQueryFieldParameterBuilder NAME_SINGLETON = new ModelKeyQueryFieldParameterBuilder(
	        ModelKeyType.NAME);
	public static final ModelKeyQueryFieldParameterBuilder NUMBER_SINGLETON = new ModelKeyQueryFieldParameterBuilder(
	        ModelKeyType.NUMBER);

	private ModelKeyType keyType;

	public ModelKeyQueryFieldParameterBuilder(ModelKeyType keyType) throws IllegalArgumentException {
		this.setKeyType(keyType);
	}

	public ModelKeyType getKeyType() {
		return this.keyType;
	}

	public void setKeyType(ModelKeyType keyType) throws IllegalArgumentException {
		if (keyType == null) {
			throw new IllegalArgumentException("Key type cannot be null.");
		}

		this.keyType = keyType;
	}

	public ModelKeyQueryFieldParameter make(String field,
	                                        ModelKey value)
	        throws IllegalArgumentException {
		return new ModelKeyQueryFieldParameter(field, value);
	}

	public ModelKeyQueryFieldParameter makeModelKeyParameter(String field,
	                                                         String parameterString)
	        throws IllegalArgumentException {
		return new ModelKeyQueryFieldParameter(field, parameterString);
	}

	/**
	 * {@link AbstractQueryFieldParameter} used for {@link ModelKey} query
	 * parameters.
	 *
	 * @author dereekb
	 *
	 */
	public class ModelKeyQueryFieldParameter extends AbstractQueryFieldParameter<ModelKey> {

		protected ModelKeyQueryFieldParameter() {};

		protected ModelKeyQueryFieldParameter(AbstractQueryFieldParameter<ModelKey> parameter)
		        throws IllegalArgumentException {
			super(parameter);
		}

		public ModelKeyQueryFieldParameter(String field, AbstractQueryFieldParameter<ModelKey> parameter)
		        throws IllegalArgumentException {
			super(field, parameter);
		}

		protected ModelKeyQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
			this.setField(field);
			this.setParameterString(parameterString);
		}

		protected ModelKeyQueryFieldParameter(String field, ModelKey value) throws IllegalArgumentException {
			this.setField(field);
			this.setValue(value);
		}

		// MARK: Override
		@Override
		public AbstractQueryFieldParameter<ModelKey> setValue(ModelKey value) throws IllegalArgumentException {
			if (value != null && value.getType() != ModelKeyQueryFieldParameterBuilder.this.keyType) {
				throw new IllegalArgumentException("Key types did not match.");
			}

			this.value = value;
			return this;
		}

		// MARK: AbstractQueryFieldParameter
		@Override
		public String getParameterValue() {
			return this.value.toString();
		}

		@Override
		public void setParameterValue(String value) throws IllegalArgumentException {
			this.value = ModelKey.convert(ModelKeyQueryFieldParameterBuilder.this.keyType, value);
		}

	}

}
