package com.dereekb.gae.utilities.misc.parameters.impl;

import com.dereekb.gae.utilities.misc.parameters.EncodedData;

/**
 * {@link EncodedData} implementation.
 * 
 * @author dereekb
 *
 */
public class EncodedDataImpl
        implements EncodedData {

	private String dataString;

	public EncodedDataImpl(String dataString) throws IllegalArgumentException {
		this.setDataString(dataString);
	}

	@Override
	public String getDataString() {
		return this.dataString;
	}

	public void setDataString(String dataString) throws IllegalArgumentException {
		if (dataString == null) {
			throw new IllegalArgumentException("dataString cannot be null.");
		}

		this.dataString = dataString;
	}

	@Override
	public String toString() {
		return "EncodedDataImpl [dataString=" + this.dataString + "]";
	}

}
