package com.dereekb.gae.web.api.model.extension.link;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;

/**
 * Delegate for {@link LinkExtensionController}.
 *
 * @author dereekb
 *
 */
public interface ApiLinkChangeConverter {

	/**
	 * Converts the input {@link ApiLinkChange} collection to
	 * {@link LinkSystemChange}.
	 *
	 * @param primaryType
	 * @param input
	 * @return
	 * @throws ConversionFailureException
	 */
	public List<LinkSystemChange> convert(String primaryType,
	                                      Collection<ApiLinkChange> input) throws ConversionFailureException;

}
