package com.dereekb.gae.utilities.time.impl;

import java.util.Date;

import com.dereekb.gae.utilities.time.IsoTimeConverter;
import com.dereekb.gae.utilities.time.SafeIsoTimeConverter;

/**
 * {@link SafeIsoTimeConverter} implementation.
 * 
 * @author dereekb
 *
 */
public class SafeIsoTimeConverterImpl
        implements SafeIsoTimeConverter {

	private IsoTimeConverter converter;

	public SafeIsoTimeConverterImpl(IsoTimeConverter converter) {
		this.setConverter(converter);
	}

	public IsoTimeConverter getConverter() {
		return this.converter;
	}

	public void setConverter(IsoTimeConverter converter) {
		if (converter == null) {
			throw new IllegalArgumentException("converter cannot be null.");
		}

		this.converter = converter;
	}

	// MARK: SafeIsoTimeConverter
	@Override
	public Date safeConvertFromString(String isoString) {
		if (isoString != null) {
			return this.convertFromString(isoString);
		} else {
			return null;
		}
	}

	@Override
	public String safeConvertToString(Date date) {
		if (date != null) {
			return this.convertToString(date);
		} else {
			return null;
		}
	}

	@Override
	public Date convertFromString(String isoString) {
		return this.converter.convertFromString(isoString);
	}

	@Override
	public String convertToString(Date date) {
		return this.converter.convertToString(date);
	}

	@Override
	public String toString() {
		return "SafeIsoTimeConverterImpl [converter=" + this.converter + "]";
	}

}
