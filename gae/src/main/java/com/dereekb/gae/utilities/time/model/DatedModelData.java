package com.dereekb.gae.utilities.time.model;

import java.util.Date;

import com.dereekb.gae.utilities.time.SafeIsoTimeConverter;
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

	public static final SafeIsoTimeConverter DATE_CONVERTER = ThreeTenIsoTimeConverter.SAFE_SINGLETON;

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
		return DATE_CONVERTER.safeConvertToString(this.date);
	}

	public void setDate(String date) {
		this.date = DATE_CONVERTER.safeConvertFromString(date);
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
