package com.dereekb.gae.utilities.query.builder.parameters.impl;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;

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
			this.setEqualityFilter(field, value);
		}

		@Override
		public void setOperator(ExpressionOperator operator) throws IllegalArgumentException {
			if (operator == null) {
				operator = ExpressionOperator.Equal;
			}

			super.setOperator(operator);
		}

		// MARK: Override
		@Override
		public AbstractQueryFieldParameter<ModelKey> setValue(ModelKey value) throws IllegalArgumentException {
			if (value != null && value.getType() != ModelKeyQueryFieldParameterBuilder.this.keyType) {
				throw new IllegalArgumentException("Key types did not match.");
			}

			return this.setValue(value);
		}

		// MARK: AbstractQueryFieldParameter
		@Override
		protected String getParameterValue() {
			return this.getValue().toString();
		}

		@Override
		protected void setParameterValue(String value) throws IllegalArgumentException {
			this.setValue(ModelKey.convert(ModelKeyQueryFieldParameterBuilder.this.keyType, value));
		}

	}

}
