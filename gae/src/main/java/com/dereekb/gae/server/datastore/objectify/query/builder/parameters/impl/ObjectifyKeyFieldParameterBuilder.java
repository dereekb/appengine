package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyModelKeyUtil;
import com.googlecode.objectify.Key;

/**
 * Builder for {@link ObjectifyKeyFieldParameter} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyKeyFieldParameterBuilder<T extends ObjectifyModel<T>> {

	private ModelKeyType keyType;
	private ObjectifyModelKeyUtil<T> util;

	public ObjectifyKeyFieldParameterBuilder(ModelKeyType keyType, Class<T> type) throws IllegalArgumentException {
		this.setKeyType(keyType);
		this.setType(type);
	}

	public static <T extends ObjectifyModel<T>> ObjectifyKeyFieldParameterBuilder<T> builder(ModelKeyType keyType,
	                                                                                         Class<T> type)
	        throws IllegalArgumentException {
		return new ObjectifyKeyFieldParameterBuilder<T>(keyType, type);
	}

	public Class<T> getType() {
		return this.util.getType();
	}

	public void setType(Class<T> type) throws IllegalArgumentException {
		if (type == null) {
			throw new IllegalArgumentException("Type cannot be null.");
		}

		this.util = new ObjectifyModelKeyUtil<T>(type);
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

	public ObjectifyKeyFieldParameter<T> make(String field,
	                                          Key<T> value) throws IllegalArgumentException {
		return new ObjectifyKeyFieldParameter<T>(this, field, value);
	}

	public ObjectifyKeyFieldParameter<T> make(String field,
	                                          String parameterString) throws IllegalArgumentException {
		return new ObjectifyKeyFieldParameter<T>(this, field, parameterString);
	}

	/**
	 * {@link AbstractQueryFieldParameter} for {@link Key} values.
	 *
	 * @author dereekb
	 *
	 */
	public static final class ObjectifyKeyFieldParameter<T extends ObjectifyModel<T>> extends AbstractQueryFieldParameter<Key<T>> {

		private final ObjectifyKeyFieldParameterBuilder<T> builder;

		private ObjectifyKeyFieldParameter(ObjectifyKeyFieldParameterBuilder<T> builder,
		        String field,
		        String parameterString) throws IllegalArgumentException {
			this.builder = builder;
			this.setField(field);
			this.setParameterString(parameterString);
		}

		private ObjectifyKeyFieldParameter(ObjectifyKeyFieldParameterBuilder<T> builder, String field, Key<T> value)
		        throws IllegalArgumentException {
			this.builder = builder;
			this.setField(field);
			this.setValue(value);
		}

		@Override
		public String getParameterValue() {
			return ObjectifyModelKeyUtil.readKeyString(this.builder.keyType, this.value);
		}

		@Override
		public void setParameterValue(String value) throws IllegalArgumentException {
			this.value = this.builder.util.keyFromString(this.builder.keyType, value);
		}

	}

}
