package com.thevisitcompany.gae.deprecated.web.api.models.support.links;

import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;
import com.thevisitcompany.gae.deprecated.web.response.models.ApiIdentifierResponse;
import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;
import com.thevisitcompany.gae.model.extension.links.service.LinksService;

/**
 * Uses a {@link LinksService} to process {@link ApiLinksChange} requests.
 * 
 * Has an optional filter regex.
 * 
 * @author dereekb
 */
public class DirectApiLinksDirectorDelegate<T extends KeyedModel<K>, K>
        implements ApiLinksDirectorDelegate<K> {

	private LinksService<T, K> linkService;
	private String filterRegex;

	@Override
	public ApiResponse handleLinksChange(ApiLinksChange<K> apiChanges)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException {

		boolean acceptable = true;
		ApiIdentifierResponse<K> response = null;
		LinksChange<K> changes = apiChanges.getChanges();

		String type = changes.getType();

		if ((apiChanges.isForced() == false) && filterRegex != null) {
			acceptable = (type.matches(this.filterRegex));
		}

		if (acceptable) {
			List<K> targets = changes.getTargets();
			List<K> identifiers = changes.getIdentifiers();
			LinksAction action = apiChanges.getAction();

			List<K> changedIds = linkService.linksChangeWithIds(targets, identifiers, action, type);
			response = new ApiIdentifierResponse<K>();
			response.setIdentifiers(changedIds);
		}

		return response;
	}

	public LinksService<T, K> getLinkService() {
		return linkService;
	}

	public void setLinkService(LinksService<T, K> linkService) {
		this.linkService = linkService;
	}

	public String getFilterRegex() {
		return filterRegex;
	}

	public void setFilterRegex(String filterRegex) {
		this.filterRegex = filterRegex;
	}
}
