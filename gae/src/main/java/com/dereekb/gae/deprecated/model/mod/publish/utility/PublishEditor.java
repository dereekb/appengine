package com.thevisitcompany.gae.deprecated.model.mod.publish.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyRegistry;
import com.thevisitcompany.visit.models.info.InfoModelUtility;
import com.thevisitcompany.visit.models.info.request.Request;
import com.thevisitcompany.visit.models.info.request.ServiceRequestType;

/**
 * Publish Editor that helps with publishing/unpublishing items.
 * 
 * @author dereekb
 * 
 * @param <T>
 */
public class PublishEditor<T extends KeyedPublishableModel<Long>> {

	private Integer requestClassIdentifier;
	private ObjectifyRegistry<Request, Long> registry;
	private PublishRulesChecker<T> rules = new PublishRulesChecker<T>();

	public PublishEditor() {}

	public PublishEditor(ObjectifyRegistry<Request, Long> registry) {
		this.registry = registry;
	}

	/**
	 * @param type
	 *            Type annotate with an InfoObjectTypeIdentifier annotation.
	 * @param registry
	 */
	public PublishEditor(Class<T> type, ObjectifyRegistry<Request, Long> registry) {
		Integer requestClassIdentifier = InfoModelUtility.getClassIdentifier(type);
		this.setRequestClassIdentifier(requestClassIdentifier);
		this.registry = registry;
	}

	/**
	 * Makes a new publish request for the given object.
	 * 
	 * @param publishable
	 */
	public Request createRequest(T publishable,
	                             ServiceRequestType type) {
		Long identifier = publishable.getId();
		Request request = new Request(type);
		request.setObjectIdentifier(identifier);
		return request;
	}

	/**
	 * Makes new publish requests for the given objects.
	 * 
	 * @param publishable
	 * @return Returns a map of objects keyed by their identifiers.
	 */
	public Map<Long, Request> createRequests(Iterable<T> publishables,
	                                         ServiceRequestType type) {
		Map<Long, Request> requests = new HashMap<Long, Request>();

		for (T publishable : publishables) {
			if (rules.canCreateRequest(publishable)) {
				Request request = this.createRequest(publishable, type);
				Long identifier = publishable.getId();
				requests.put(identifier, request);
			}
		}

		return requests;
	}

	/**
	 * Publishes the given object, and removes any outstanding requests.
	 */
	public List<T> publish(Iterable<T> publishables) {
		List<T> publishedList = new ArrayList<T>();

		for (T publishable : publishables) {
			if (rules.canPublish(publishable)) {
				publishable.setPublished(true);
				publishedList.add(publishable);
			}
		}

		this.deleteRequests(publishedList);
		return publishedList;
	}

	/**
	 * Unpublishes the given object, and removes any outstanding requests.
	 */
	public List<T> unpublish(Iterable<T> publishables) {
		List<T> unpublishedList = new ArrayList<T>();

		for (T publishable : publishables) {
			if (rules.canUnpublish(publishable)) {
				publishable.setPublished(false);
				unpublishedList.add(publishable);
			}
		}

		this.deleteRequests(unpublishedList);
		return unpublishedList;
	}

	/**
	 * Creates new requests to publish the target item.
	 */
	public List<T> requestPublish(Iterable<T> publishables) {
		List<T> requestAbleList = new ArrayList<T>();

		for (T publishable : publishables) {
			if (rules.canRequestPublishing(publishable)) {
				requestAbleList.add(publishable);
			}
		}

		Map<Long, Request> requests = this.createRequests(publishables, ServiceRequestType.Publish);
		this.saveRequests(requests.values());

		for (T publishable : requestAbleList) {
			Request request = requests.get(publishable.getId());
			Key<Request> requestKey = request.getKey();
			publishable.setPublishRequest(requestKey);
		}

		return requestAbleList;
	}

	/**
	 * Creates new requests to unpublish the target item.
	 */
	public List<T> requestUnpublish(Iterable<T> publishables) {
		List<T> requestAbleList = new ArrayList<T>();

		for (T publishable : publishables) {
			if (rules.canRequestUnpublishing(publishable)) {
				requestAbleList.add(publishable);
			}
		}

		Map<Long, Request> requests = this.createRequests(publishables, ServiceRequestType.Unpublish);
		this.saveRequests(requests.values());

		for (T publishable : requestAbleList) {
			Request request = requests.get(publishable.getId());
			Key<Request> requestKey = request.getKey();
			publishable.setPublishRequest(requestKey);
		}

		return requestAbleList;
	}

	/**
	 * Deletes the current request for the given object.
	 * 
	 * @param publishable
	 */
	public boolean deleteRequest(T publishable) {
		Key<Request> request = publishable.getPublishRequest();
		boolean canDelete = (request != null);

		if (request != null) {
			Key<Request> requestKey = publishable.getPublishRequest();
			this.registry.delete(requestKey, true);
		}

		return canDelete;
	}

	/**
	 * Deletes the current requests for the given objects.
	 * 
	 * @param publishables
	 */
	public void deleteRequests(Iterable<T> publishables) {
		List<Key<Request>> requests = new ArrayList<Key<Request>>();

		for (T publishable : publishables) {
			Key<Request> request = publishable.getPublishRequest();

			if (request != null) {
				requests.add(request);
				publishable.setPublishRequest(null);
			}
		}

		this.registry.deleteWithObjectifyKeys(requests, true);
	}

	/**
	 * Saves the requests to the datastore.
	 * 
	 * @param values
	 */
	private void saveRequests(Collection<Request> values) {
		this.registry.save(values, false);
	}

	public Integer getRequestClassIdentifier() {
		return requestClassIdentifier;
	}

	public void setRequestClassIdentifier(Integer requestClassIdentifier) throws NullPointerException {
		if (requestClassIdentifier == null) {
			throw new NullPointerException();
		}

		this.requestClassIdentifier = requestClassIdentifier;
	}

	public ObjectifyRegistry<Request, Long> getRegistry() {
		return registry;
	}

	public void setRegistry(ObjectifyRegistry<Request, Long> registry) {
		this.registry = registry;
	}

	public PublishRulesChecker<T> getRules() {
		return rules;
	}

	public void setRules(PublishRulesChecker<T> rules) {
		this.rules = rules;
	}

}
