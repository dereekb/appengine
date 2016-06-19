package com.dereekb.gae.server.datastore.models.dto;

import java.io.Serializable;
import java.util.Date;

import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Base Data Transfer Model (DTO) for a {@link DatabaseModel}.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DatabaseModelData
        implements Serializable, UniqueModel {

	private static final long serialVersionUID = 1L;

	/**
	 * The model's database identifier as a String.
	 */
	protected String key;

	/**
	 * Date that the model was created encoded in a Long.
	 */
	protected Long created;

	public DatabaseModelData() {}

	public DatabaseModelData(String identifier) {
		this.key = identifier;
	}

	public DatabaseModelData(String identifier, Long creationTime) {
		this.key = identifier;
		this.created = creationTime;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@JsonIgnore
	public void setIdentifier(ModelKey key) {
		this.key = ModelKey.readStringKey(key);
	}

	@JsonIgnore
	public void setIdentifier(Long identifier) {
		String idString = null;

		if (identifier != null) {
			idString = identifier.toString();
		}

		this.key = idString;
	}

	public Long getCreated() {
		return this.created;
	}

	public void setCreated(Long creationTime) {
		this.created = creationTime;
	}

	@JsonIgnore
	public void setCreated(Date creationDate) {
		Long time = null;

		if (creationDate != null) {
			time = creationDate.getTime();
		}

		this.created = time;
	}

	// UniqueModel
	/**
	 * Override in super classes to convert to expected type.
	 */
	@JsonIgnore
	@Deprecated
	@Override
	public ModelKey getModelKey() {
		return ModelKey.convert(this.key);
	}

}
