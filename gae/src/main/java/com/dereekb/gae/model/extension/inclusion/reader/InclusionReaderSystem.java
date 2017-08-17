package com.dereekb.gae.model.extension.inclusion.reader;

import com.dereekb.gae.model.extension.read.exception.UnavailableTypesException;

/**
 * System that provides {@link InclusionReader} instances.
 * 
 * @author dereekb
 *
 */
public interface InclusionReaderSystem {

	/**
	 * Returns an inclusion reader for the requested type.
	 * 
	 * @param modelType
	 *            {@link String}. Never {@code null}.
	 * @return {@link InclusionReader}. Never {@code null}.
	 * 
	 * @throws UnavailableTypesException
	 *             thrown if the requested type is unavailable.
	 */
	public InclusionReader getReaderForType(String modelType) throws UnavailableTypesException;

}
