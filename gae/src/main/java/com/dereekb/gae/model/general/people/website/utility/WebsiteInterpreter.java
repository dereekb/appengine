package com.dereekb.gae.model.general.people.website.utility;

import com.dereekb.gae.model.general.people.website.WebsiteAddress;

/**
 * Used for interpreting a single website.
 *
 * @author dereekb
 *
 */
public interface WebsiteInterpreter {

	public WebsiteAddress convertToAddress(InterpretedWebsite website);

	public InterpretedWebsite convertFromAddress(WebsiteAddress address);

}
