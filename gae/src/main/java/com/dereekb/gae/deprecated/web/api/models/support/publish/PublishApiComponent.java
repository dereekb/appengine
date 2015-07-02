package com.thevisitcompany.gae.deprecated.web.api.models.support.publish;

import java.util.Collection;
import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.PublishAction;
import com.thevisitcompany.gae.deprecated.model.mod.publish.service.PublishService;
import com.thevisitcompany.gae.deprecated.web.exceptions.ForbiddenModelRequestException;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnavailableModelsRequestException;
import com.thevisitcompany.gae.deprecated.web.response.models.PublishResponse;

public class PublishApiComponent<T extends KeyedPublishableModel<K>, K> {

	private final PublishService<T, K> service;

	public PublishApiComponent(PublishService<T, K> service) {
		this.service = service;
	}

	public PublishResponse<K> changePublish(List<K> identifiers,
	                                        PublishAction action) {
		PublishResponse<K> response = new PublishResponse<K>();

		try {
			Collection<K> changedIdentifiers = this.service.publishChangeWithIds(identifiers, action);
			response.setIdentifiers(changedIdentifiers);

		} catch (ForbiddenObjectChangesException e) {
			throw new ForbiddenModelRequestException(response);
		} catch (UnavailableObjectsException e) {
			throw new UnavailableModelsRequestException(e, response);
		}

		return response;
	}

}
