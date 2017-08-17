package com.dereekb.gae.model.extension.links.deleter;

/**
 * Used for un-linking and potentially deleting models that are linked to a
 * model that is being deleted.
 *
 * @author dereekb
 * @deprecated Deletes should not be handled by a dedicated service.
 *             Realistically, the should be handled by review tasks that go on
 *             to perform changes.
 */
@Deprecated
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
