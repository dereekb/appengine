package com.dereekb.gae.server.datastore.models;

import java.io.Serializable;

import com.dereekb.gae.server.datastore.models.keys.KeyEquality;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * A database model.
 *
 * @author dereekb
 */
public abstract class DatabaseModel
        implements UniqueModel, KeyEquality, Serializable {

	private static final long serialVersionUID = 1L;

	protected DatabaseModel() {}

	protected abstract Object getDatabaseIdentifier();

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 0;
		Object identifier = this.getDatabaseIdentifier();

		if (identifier == null) {
			result = super.hashCode();
		} else {
			result = identifier.hashCode();
		}

		result = prime * result;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
	        return true;
        }
		if (obj == null) {
	        return false;
        }
		if (this.getClass() != obj.getClass()) {
	        return false;
        }

		DatabaseModel other = (DatabaseModel) obj;
		return this.keysEqual(other);
	}

	@Override
	public boolean keysEqual(UniqueModel model) {
		ModelKey key = this.getModelKey();
		ModelKey otherKey = model.getModelKey();

		if (key == null) {
			if (otherKey != null) {
				return false;
			}
		} else if (!key.equals(otherKey)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		ModelKey key = this.getModelKey();
		return "Model [key=" + key + "]";
	}

}