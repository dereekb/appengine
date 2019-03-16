package com.thevisitcompany.gae.deprecated.model.mod.publish.utility;

import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.server.datastore.Getter;
import com.thevisitcompany.gae.server.datastore.objectify.query.ObjectifyQuery;
import com.thevisitcompany.visit.models.info.InfoModelUtility;
import com.thevisitcompany.visit.models.info.request.Request;
import com.thevisitcompany.visit.models.info.request.RequestRegistry;
import com.thevisitcompany.visit.models.info.request.ServiceRequestType;
import com.thevisitcompany.visit.models.info.request.search.RequestQueryRequest;

/**
 * Utility for helping search objects of a given type.
 * 
 * @author dereekb
 * 
 */
// TODO: Convert to this to another type of Function, since in this state it is unable to securely filter.
public class PublishRequestSearchUtility<T extends KeyedPublishableModel<Long>, K> {

	private final static Integer publishRequestType = ServiceRequestType.Publish.getId();

	private final RequestRegistry registry = RequestRegistry.getRegistry();
	private final Getter<T, Long> service;
	private final Class<T> type;
	private Integer limit;

	public PublishRequestSearchUtility(Getter<T, Long> service, Class<T> type) {
		this.service = service;
		this.type = type;
	}

	public PublishRequestSearchUtility(Getter<T, Long> service, Class<T> type, Integer limit) {
		this.service = service;
		this.type = type;
		this.limit = limit;
	}

	public List<T> searchModelsRequestingPublish() throws UnavailableObjectsException {
		List<Long> identifiers = this.searchModelIdentifiersRequestingPublish();
		List<T> models = service.getWithKeys(identifiers);
		return models;
	}

	public List<Long> searchModelIdentifiersRequestingPublish() {
		List<Request> requests = this.queryRequests();
		List<Long> identifiers = InfoModelUtility.readObjectIdentifiers(requests);
		return identifiers;
	}

	private List<Request> queryRequests() {
		RequestQueryRequest request = RequestQueryRequest.queryRequestsOfTypeForObject(publishRequestType, type);
		ObjectifyQuery<Request> query = request.getQuery();

		if (this.limit != null) {
			query.setLimit(this.limit);
		}

		List<Request> requests = registry.queryEntities(query);
		return requests;
	}

}
