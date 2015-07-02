package com.thevisitcompany.gae.deprecated.web.api.models.support.links;

import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.deprecated.web.exceptions.ForbiddenModelRequestException;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnavailableModelsRequestException;
import com.thevisitcompany.gae.deprecated.web.response.models.LinkResponse;
import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;
import com.thevisitcompany.gae.model.extension.links.service.LinksService;

@Deprecated
public class LinksApiComponent<T extends KeyedModel<K>, K> {

	private final LinksService<T, K> service;

	public LinksApiComponent(LinksService<T, K> service) {
		this.service = service;
	}

	public LinkResponse<K> changeLinks(LinksChange<K> changes,
	                                   LinksAction action) {
		LinkResponse<K> response = new LinkResponse<K>();

		try {
			String type = changes.getType();
			List<K> targets = changes.getTargets();
			List<K> identifiers = changes.getIdentifiers();

			List<K> changedIdentifiers = this.service.linksChangeWithIds(targets, identifiers, action, type);

			response.setIdentifiers(changedIdentifiers);

		} catch (ForbiddenObjectChangesException e) {
			throw new ForbiddenModelRequestException(response);
		} catch (UnavailableObjectsException e) {
			throw new UnavailableModelsRequestException(e, response);
		}

		return response;
	}

}
