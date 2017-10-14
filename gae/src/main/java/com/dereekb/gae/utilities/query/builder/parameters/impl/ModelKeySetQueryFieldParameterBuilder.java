package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.utilities.query.builder.parameters.QueryParameter;

/**
 * Builder for a {@link ModelKeySetQueryFieldParameter}.
 * 
 * @author dereekb
 *
 */
public class ModelKeySetQueryFieldParameterBuilder {

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
	public class ModelKeySetQueryFieldParameter extends AbstractSetQueryFieldParameter<ModelKey> {

		public ModelKeySetQueryFieldParameter() {
			super();
		}

		public ModelKeySetQueryFieldParameter(AbstractQueryFieldParameter<Set<ModelKey>> parameter)
		        throws IllegalArgumentException {
			super(parameter);
		}

		public ModelKeySetQueryFieldParameter(String field, AbstractQueryFieldParameter<Set<ModelKey>> parameter)
		        throws IllegalArgumentException {
			super(field, parameter);
		}

		public ModelKeySetQueryFieldParameter(String field, Collection<ModelKey> value)
		        throws IllegalArgumentException {
			super(field, value);
		}

		public ModelKeySetQueryFieldParameter(String field, ModelKey value) throws IllegalArgumentException {
			super(field, value);
		}

		public ModelKeySetQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
			super(field, parameterString);
		}

		@Override
		protected void assertSingleValueIsValid(ModelKey value) {
			super.assertSingleValueIsValid(value);
			if (value.getType() != ModelKeySetQueryFieldParameterBuilder.this.keyType) {
				throw new IllegalArgumentException("Key types did not match.");
			}
		}

		// MARK: AbstractQueryFieldParameter
		@Override
		protected String encodeValuesFromString(Collection<ModelKey> values) {
			return ModelKey.keysAsString(values);
		}

		@Override
		protected Collection<ModelKey> decodeValuesFromString(String value) {
			return ModelKeySetQueryFieldParameterBuilder.this.decodeModelKeysFromValue(value);
		}

	}

}
