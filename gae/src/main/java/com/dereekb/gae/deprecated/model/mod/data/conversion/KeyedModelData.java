package com.thevisitcompany.gae.deprecated.model.mod.data.conversion;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;

/**
 * Keyed Model Data Objects are Model Data with identifiers that belong to the model it represents.
 *
 * @author dereekb
 *
 */
@Deprecated
public abstract class KeyedModelData<K>
        implements KeyedModel<K>, Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	protected K id;

	private Long creationTime;

	@Override
    public K getId() {
		return this.id;
	}

	public void setId(K id) {
		this.id = id;
	}

	public Long getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}

	public void setCreationDate(Date creationDate) throws NullPointerException {
		if (creationDate == null) {
			throw new NullPointerException("Creation date was null.");
		}

		this.creationTime = creationDate.getTime();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
	        return true;
        }
		if (obj == null) {
	        return false;
        }
		if (this.getClass() != obj.getClass()) {
	        return false;
        }
		KeyedModelData<?> other = (KeyedModelData<?>) obj;
		if (this.id == null) {
			if (other.id != null) {
	            return false;
            }
		} else if (!this.id.equals(other.id)) {
	        return false;
        }
		return true;
	}

}
