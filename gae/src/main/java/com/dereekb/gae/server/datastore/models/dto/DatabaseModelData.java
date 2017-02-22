package com.dereekb.gae.server.datastore.models.dto;

import java.io.Serializable;
import java.util.Date;

import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.time.IsoTimeConverter;
import com.dereekb.gae.utilities.time.impl.ThreeTenIsoTimeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Base Data Transfer Model (DTO) for a {@link DatabaseModel}.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DatabaseModelData
        implements Serializable, UniqueModel, SimpleKeyModel {

	private static final long serialVersionUID = 1L;

	public static final IsoTimeConverter DATE_CONVERTER = ThreeTenIsoTimeConverter.SINGLETON;

	/**
	 * The model's database identifier as a String.
	 */
	protected String key;

	/**
	 * Date that the model was created. For JSON serialization is is converted
	 * to a string.
	 */
	protected Date date;

	public DatabaseModelData() {}

	public DatabaseModelData(String key) {
		this.setKey(key);
	}

	public DatabaseModelData(String key, Date date) {
		this.setKey(key);
		this.setDateValue(date);
	}

	@Deprecated
	public DatabaseModelData(String key, Long creationTime) {
		this.setKey(key);
		this.setCreated(creationTime);
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}

	@JsonIgnore
	public void setModelKey(ModelKey key) {
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

	@JsonIgnore
	@Deprecated
	public Long getCreated() {
		Long time = null;

		if (this.date != null) {
			time = this.date.getTime();
		}

		return time;
	}

	@JsonIgnore
	@Deprecated
	public void setCreated(Long creationTime) {
		Date date = null;

		if (creationTime != null) {
			date = new Date(creationTime);
		}

		this.setDateValue(date);
	}

	public String getDate() {
		String value = null;

		if (this.date != null) {
			value = DATE_CONVERTER.convertToString(this.date);
		}

		return value;
	}

	public void setDate(String date) {
		Date value = null;

		if (date != null) {
			value = DATE_CONVERTER.convertFromString(date);
		}

		this.date = value;
	}

	@JsonIgnore
	public Date getDateValue() {
		return this.date;
	}

	@JsonIgnore
	public void setDateValue(Date date) {
		this.date = date;
	}

	// UniqueModel
	@Override
	public ModelKey getModelKey() {
		return ModelKey.convertNumberString(this.key);
	}

	@Override
	public ModelKey keyValue() {
		return this.getModelKey();
	}

}
