package com.dereekb.gae.utilities.time.model;

import java.util.Date;

import com.dereekb.gae.utilities.time.IsoTimeConverter;
import com.dereekb.gae.utilities.time.impl.ThreeTenIsoTimeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Basic DTO that contains a date.
 * <p>
 * The date value is encoded to a string for JSON serialization.
 * 
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatedModelData {

	public static final IsoTimeConverter DATE_CONVERTER = ThreeTenIsoTimeConverter.SINGLETON;

	/**
	 * Date for the model. For JSON serialization it is converted
	 * to a string.
	 */
	protected Date date;

	public DatedModelData() {}

	public DatedModelData(Date date) {
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

}
