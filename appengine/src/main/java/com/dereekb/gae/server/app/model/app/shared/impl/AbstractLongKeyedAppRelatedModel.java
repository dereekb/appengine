package com.dereekb.gae.server.app.model.app.shared.impl;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Id;

/**
 * Abstract {@link AbstractAppRelatedModel} implementation with a
 * {@link String} identifier.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractLongKeyedAppRelatedModel<T> extends AbstractAppRelatedModel<T> {

	private static final long serialVersionUID = 1L;

	public static final ModelKeyType MODEL_KEY_TYPE = ModelKeyType.NUMBER;

	/**
	 * Database identifier.
	 */
	@Id
	protected Long identifier;

	public AbstractLongKeyedAppRelatedModel() {}

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return ModelKey.safe(this.identifier);
	}

	@Override
	public void setModelKey(ModelKey key) {
		this.identifier = ModelKey.readIdentifier(key);
	}

	// Database Model
	@Override
	protected Object getDatabaseIdentifier() {
		return this.identifier;
	}

	// Objectify Model
	@Override
	public abstract Key<T> getObjectifyKey();

}
