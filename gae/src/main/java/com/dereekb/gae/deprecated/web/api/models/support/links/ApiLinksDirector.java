package com.thevisitcompany.gae.deprecated.web.api.models.support.links;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.deprecated.web.exceptions.ApiRuntimeException;
import com.thevisitcompany.gae.deprecated.web.exceptions.ForbiddenModelRequestException;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnavailableModelsRequestException;
import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;
import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;

/**
 * Controller for linking objects together.
 *
 * @author dereekb
 *
 * @param <T>
 * @param <K>
 */
public class ApiLinksDirector<T extends KeyedModel<K>, K> {

	private ApiLinksDirectorDelegate<K> delegate;

	public ApiResponse handleLinksChange(LinksAction action,
	                                     LinksChange<K> changes,
	                                     Boolean forced)
	        throws ForbiddenModelRequestException,
	            UnavailableModelsRequestException {
		ApiLinksChange<K> apiChanges = new ApiLinksChange<K>(action, changes, forced);
		return this.handleLinksChange(apiChanges);
	}

	public ApiResponse handleLinksChange(ApiLinksChange<K> changes)
	        throws ForbiddenModelRequestException,
	            UnavailableModelsRequestException,
	            ApiLinksChangeUnavailableException {
		ApiResponse response = null;

		try {
			response = this.delegate.handleLinksChange(changes);

			if (response == null) {
				throw ApiLinksChangeUnavailableException.with(changes);
			}
		} catch (ForbiddenObjectChangesException e) {
			throw new ForbiddenModelRequestException(response);
		} catch (UnavailableObjectsException e) {
			throw new UnavailableModelsRequestException(e, response);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	public ApiLinksDirectorDelegate<K> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ApiLinksDirectorDelegate<K> delegate) {
		this.delegate = delegate;
	}

}
