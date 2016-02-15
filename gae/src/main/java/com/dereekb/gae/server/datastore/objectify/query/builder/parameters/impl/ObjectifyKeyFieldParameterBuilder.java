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

	public ObjectifyKeyFieldParameter make(String field,
	                                       Key<T> value) throws IllegalArgumentException {
		return new ObjectifyKeyFieldParameter(field, value);
	}

	/**
	 * {@link AbstractQueryFieldParameter} for {@link Key} values.
	 * 
	 * @author dereekb
	 *
	 */
	public class ObjectifyKeyFieldParameter extends AbstractQueryFieldParameter<Key<T>> {

		private ObjectifyKeyFieldParameter(String field, Key<T> value) throws IllegalArgumentException {
			super(field, value);
		}

		@Override
		public String getParameterValue() {
			return ObjectifyModelKeyUtil.readKeyString(ObjectifyKeyFieldParameterBuilder.this.keyType, this.value);
		}

		@Override
		public void setParameterValue(String value) throws IllegalArgumentException {
			this.value = ObjectifyKeyFieldParameterBuilder.this.util.keyFromString(
			        ObjectifyKeyFieldParameterBuilder.this.keyType, value);
		}

	}

}
