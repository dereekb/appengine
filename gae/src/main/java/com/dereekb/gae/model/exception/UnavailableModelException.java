package com.dereekb.gae.model.exception;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;

/**
 * Exception for {@link ModelKey} based requests that require a model that is
 * unavailable.
 * <p>
 * Might sometimes not include any models keys.
 *
 * @author dereekb
 *
 */
public class UnavailableModelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Set<ModelKey> modelKeys;

	public UnavailableModelException(String message) {
		this((Collection<ModelKey>) null, message);
	}
	
	public UnavailableModelException(ModelKey modelKey) {
		this(modelKey, null);
	}
	
	public UnavailableModelException(Collection<ModelKey> modelKeys) {
		this(modelKeys, null);
	}

	public UnavailableModelException(ModelKey modelKey, String message) {
		this(SetUtility.wrap(modelKey), message);
	}

	public UnavailableModelException(Collection<ModelKey> modelKeys, String message) {
		super(message);
		
		if (modelKeys != null) {
			this.modelKeys = new HashSet<ModelKey>(modelKeys);
		} else {
			this.modelKeys = Collections.emptySet();
		}
		
	}
	
	public Set<ModelKey> getModelKeys() {
		return this.modelKeys;
	}

	@Override
	public String toString() {
		return "UnavailableModelException [modelKeys=" + this.modelKeys + "]";
	}

}
