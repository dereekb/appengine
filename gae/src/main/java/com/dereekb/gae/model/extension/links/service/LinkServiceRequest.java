package com.dereekb.gae.model.extension.links.service;

import java.util.List;

/**
 * Request for {@link LinkService}.
 *
 * @author dereekb
 */
public interface LinkServiceRequest {

	/**
	 * Returns the list of link changes to make.
	 *
	 * @return list of link changes to make. Never null.
	 */
	public List<LinkChange> getLinkChanges();

}
