package com.dereekb.gae.utilities.misc.parameters;

/**
 * Interface for data that can be serialized to a string.
 * 
 * @author dereekb
 *
 */
public interface EncodedData {

	/**
	 * Returns the data encoded as a string.
	 * 
	 * @return {@link String} of data. Never {@code null}.
	 */
	public String getDataString();

}
