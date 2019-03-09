package com.dereekb.gae.model.extension.links.deleter;


/**
 * Used for un-linking and potentially deleting models that are linked to a
 * model that is being deleted.
 *
 * @author dereekb
 */
public interface LinkDeleterService {

	/**
	 * Processes the request. Models that are unavailable in the request are
	 * skipped and ignored.
	 *
	 * @param request
	 *            Request. Never {@code null}.
	 */
	public void deleteLinks(LinkDeleterServiceRequest request);

}
