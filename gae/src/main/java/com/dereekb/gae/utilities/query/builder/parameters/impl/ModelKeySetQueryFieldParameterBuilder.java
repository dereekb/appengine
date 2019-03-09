package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.query.builder.parameters.QueryParameter;

public class ModelKeySetQueryFieldParameterBuilder {

	public static final Integer MAX_KEYS_ALLOWED = 30;

	private static final Set<ExpressionOperator> ALLOWED_OPERATORS = SetUtility.makeSet(ExpressionOperator.IN,
	        ExpressionOperator.EQUAL);

	public static final ModelKeySetQueryFieldParameterBuilder NAME_SINGLETON = new ModelKeySetQueryFieldParameterBuilder(
	        ModelKeyType.NAME);
	public static final ModelKeySetQueryFieldParameterBuilder NUMBER_SINGLETON = new ModelKeySetQueryFieldParameterBuilder(
	        ModelKeyType.NUMBER);

	private ModelKeyType keyType;

	protected ModelKeySetQueryFieldParameterBuilder(ModelKeyType keyType) throws IllegalArgumentException {
		this.setKeyType(keyType);
	}

	public static ModelKeySetQueryFieldParameterBuilder make(ModelKeyType type) throws IllegalArgumentException {
		switch (type) {
			case NAME:
				return NAME_SINGLETON;
			case NUMBER:
				return NUMBER_SINGLETON;
			default:
				throw new IllegalArgumentException("Disallowed key type.");
		}
	}

	public static ModelKeySetQueryFieldParameterBuilder name() {
		return NAME_SINGLETON;
	}

	public static ModelKeySetQueryFieldParameterBuilder number() {
		return NUMBER_SINGLETON;
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

	public ModelKeySetQueryFieldParameter make(String field,
	                                           ModelKey value)
	        throws IllegalArgumentException {
		ModelKeySetQueryFieldParameter fieldParameter = null;

		if (value != null) {
			fieldParameter = new ModelKeySetQueryFieldParameter(field, value);
		}

		return fieldParameter;
	}

	public ModelKeySetQueryFieldParameter make(String field,
	                                           Collection<ModelKey> value)
	        throws IllegalArgumentException {
		ModelKeySetQueryFieldParameter fieldParameter = null;

		if (value != null) {
			fieldParameter = new ModelKeySetQueryFieldParameter(field, value);
		}

		return fieldParameter;
	}

	public ModelKeySetQueryFieldParameter make(String field,
	                                           ModelKeySetQueryFieldParameter value)
	        throws IllegalArgumentException {
		ModelKeySetQueryFieldParameter fieldParameter = null;

		if (value != null) {
			fieldParameter = new ModelKeySetQueryFieldParameter(field, value);
		}

		return fieldParameter;
	}

	public ModelKeySetQueryFieldParameter makeModelKeyParameter(String field,
	                                                            String parameterString)
	        throws IllegalArgumentException {
		ModelKeySetQueryFieldParameter fieldParameter = null;

		if (parameterString != null) {
			fieldParameter = new ModelKeySetQueryFieldParameter(field, parameterString);
		}

		return fieldParameter;
	}

	public List<ModelKey> decodeModelKeysFromParameter(String parameterString) throws IllegalArgumentException {
		QueryParameter parameter = QueryFieldParameterDencoder.SINGLETON.decodeString(parameterString);
		return this.decodeModelKeysFromValue(parameter.getValue());
	}

	public List<ModelKey> decodeModelKeysFromValue(String value) throws IllegalArgumentException {
		return ModelKey.convertKeysInString(this.keyType, value);
	}

	/**
	 * {@link AbstractQueryFieldParameter} used for {@link ModelKey} query
	 * parameters.
	 *
	 * @author dereekb
	 *
	 */
	public class ModelKeySetQueryFieldParameter extends AbstractQueryFieldParameter<Set<ModelKey>> {

		protected ModelKeySetQueryFieldParameter() {};

		protected ModelKeySetQueryFieldParameter(AbstractQueryFieldParameter<Set<ModelKey>> parameter)
		        throws IllegalArgumentException {
			super(parameter);
		}

		public ModelKeySetQueryFieldParameter(String field, AbstractQueryFieldParameter<Set<ModelKey>> parameter)
		        throws IllegalArgumentException {
			super(field, parameter);
		}

		protected ModelKeySetQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
			this.setField(field);
			this.setParameterString(parameterString);
		}

		protected ModelKeySetQueryFieldParameter(String field, ModelKey value) throws IllegalArgumentException {
			this.setField(field);
			this.setValue(value);
			this.setOperator(ExpressionOperator.IN);
		}

		protected ModelKeySetQueryFieldParameter(String field, Collection<ModelKey> value)
		        throws IllegalArgumentException {
			this.setField(field);
			this.setValue(value);
			this.setOperator(ExpressionOperator.IN);
		}

		// MARK: Override
		public AbstractQueryFieldParameter<Set<ModelKey>> setValue(Collection<ModelKey> value) {
			if (value.size() > MAX_KEYS_ALLOWED) {
				throw new IllegalArgumentException("Only " + MAX_KEYS_ALLOWED + " keys are allowed for this query.");
			}

			Set<ModelKey> set = new HashSet<ModelKey>(value);
			return this.setValue(set);
		}

		@Override
		public AbstractQueryFieldParameter<Set<ModelKey>> setValue(Set<ModelKey> value) {
			if (value == null || value.isEmpty()) {
				throw new IllegalArgumentException("Set cannot be empty.");
			}

			Set<ModelKey> set = new HashSet<ModelKey>(value);
			return super.setValue(set);
		}

		public AbstractQueryFieldParameter<Set<ModelKey>> setValue(ModelKey value) {
			if (value == null) {
				throw new IllegalArgumentException("Single value cannot be null.");
			} else if (value.getType() != ModelKeySetQueryFieldParameterBuilder.this.keyType) {
				throw new IllegalArgumentException("Key types did not match.");
			}

			Set<ModelKey> set = new HashSet<ModelKey>();
			set.add(value);

			return super.setValue(set);
		}

		@Override
		public void setOperator(ExpressionOperator operator) throws IllegalArgumentException {
			if (ALLOWED_OPERATORS.contains(operator) == false) {
				throw new IllegalArgumentException("Disallowed operator. Try using IN.");
			}

			super.setOperator(operator);
		}

		// MARK: AbstractQueryFieldParameter
		@Override
		protected String getParameterValue() {
			Set<ModelKey> keys = this.getValue();
			return ModelKey.keysAsString(keys);
		}

		@Override
		protected void setParameterValue(String value) throws IllegalArgumentException {
			this.setValue(ModelKeySetQueryFieldParameterBuilder.this.decodeModelKeysFromValue(value));
		}

	}

}
