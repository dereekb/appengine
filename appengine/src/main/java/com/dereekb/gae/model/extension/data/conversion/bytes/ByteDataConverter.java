package com.dereekb.gae.model.extension.data.conversion.bytes;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;



/**
 * Converts an object to/from byte data.
 *
 * @author dereekb
 */
public interface ByteDataConverter<T> {

	/**
	 * @return Content type of the exported bytes. Ex: "application/json".
	 */
	public String getByteContentType();

	public byte[] convertToBytes(T object) throws ConversionFailureException;

	public T convertToObject(byte[] bytes) throws ConversionFailureException;

}
