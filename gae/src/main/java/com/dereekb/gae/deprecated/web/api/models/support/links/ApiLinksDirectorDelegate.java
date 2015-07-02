package com.thevisitcompany.gae.deprecated.web.api.models.support.links;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;

/**
 * Delegate for an {@link ApiLinksDirector}.
 *
 * @author dereekb
 */
public interface ApiLinksDirectorDelegate<K> {

	public ApiResponse handleLinksChange(ApiLinksChange<K> changes)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException;

}
