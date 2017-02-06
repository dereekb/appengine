package com.dereekb.gae.model.extension.links.deprecated.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.components.AtomicReadService;
import com.dereekb.gae.model.extension.links.deprecated.exception.ForbiddenLinkChangeException;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksAction;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksFunction;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;

/**
 * Helper component that uses a readService and linksFunction to implement a LinksService.
 *
 * @author dereekb
 */
@Deprecated
public class LinksServiceComponent<T extends UniqueModel>
        implements OpenLinksService<T> {

	private final AtomicReadService<T> readService;
	private final LinksFunction<T> linksFunction;

	public LinksServiceComponent(AtomicReadService<T> readService, LinksFunction<T> linksFunction) {
		this.readService = readService;
		this.linksFunction = linksFunction;
	}

	@Override
    public List<T> linksChange(Collection<LinksPair<T>> pairs) {
		this.linksFunction.addObjects(pairs);
		boolean success = this.linksFunction.run();

		List<T> changed = Collections.emptyList();

		if (success) {
			changed = this.getSuccessfulItems(pairs);
		}

		return changed;
	}

	protected List<T> getSuccessfulItems(Collection<LinksPair<T>> pairs) {
		List<T> successful = new ArrayList<T>();

		for (LinksPair<T> pair : pairs) {
			if (pair.isSuccessful()) {
				successful.add(pair.getTarget());
			}
		}

		return successful;
	}

	@Override
	public List<T> linksChange(Collection<T> targets,
	                           Collection<ModelKey> linkKeys,
	                           LinksAction action,
	                           String type) throws ForbiddenLinkChangeException {

		List<LinksPair<T>> pairs = new ArrayList<LinksPair<T>>();

		for (T target : targets) {
			LinksPair<T> pair = new LinksPair<T>(target, linkKeys, type, action);
			pairs.add(pair);
		}

		return this.linksChange(pairs);
	}

	@Override
	public List<ModelKey> linksChangeWithIds(Collection<ModelKey> targetKeys,
	                                         Collection<ModelKey> linkKeys,
	                                  LinksAction action,
	                                         String type)
 throws ForbiddenLinkChangeException {

		// TODO: Update with new use of readService
		Collection<T> models = this.readService.read(targetKeys);
		Collection<T> changedModels = this.linksChange(models, linkKeys, action, type);
		List<ModelKey> changedKeys = ModelKey.readModelKeys(changedModels);
		return changedKeys;
	}

	@Override
	public List<ModelKey> linksChangeWithRequest(LinksServiceKeyRequest request)
 throws ForbiddenLinkChangeException {
		return this.linksChangeWithRequests(SingleItem.withValue(request));
	}

	@Override
    public List<ModelKey> linksChangeWithRequests(Iterable<LinksServiceKeyRequest> requests)
	        throws ForbiddenLinkChangeException {

		List<LinksPair<T>> pairs = this.buildPairsFromRequests(requests);
		Collection<T> changedModels = this.linksChange(pairs);
		List<ModelKey> changedKeys = ModelKey.readModelKeys(changedModels);
		return changedKeys;
	}

	@Override
	public List<T> linksChangeWithModelRequest(LinksServiceModelRequest<T> request)
 throws ForbiddenLinkChangeException {
		return this.linksChangeWithModelRequests(SingleItem.withValue(request));
	}

	@Override
	public List<T> linksChangeWithModelRequests(Iterable<LinksServiceModelRequest<T>> requests)
	        throws ForbiddenLinkChangeException {

		List<LinksPair<T>> pairs = this.buildPairsFromModelRequests(requests);
		List<T> changedModels = this.linksChange(pairs);
		return changedModels;
	}

	private List<LinksPair<T>> buildPairsFromRequests(Iterable<LinksServiceKeyRequest> requests)
	        throws ForbiddenLinkChangeException {

		HashMapWithList<ModelKey, LinksServiceKeyRequest> map = new HashMapWithList<ModelKey, LinksServiceKeyRequest>();

		for (LinksServiceKeyRequest request : requests) {
			Collection<ModelKey> targetKeys = request.getTargetKeys();

			for (ModelKey targetKey : targetKeys) {
				map.add(targetKey, request);
			}
		}

		Set<ModelKey> targetKeys = map.getKeySet();
		Collection<T> models = this.readService.read(targetKeys);
		List<LinksPair<T>> finalPairs = this.makeFinalPairs(map, models);
		return finalPairs;
	}

	private List<LinksPair<T>> buildPairsFromModelRequests(Iterable<LinksServiceModelRequest<T>> requests)
	        throws ForbiddenLinkChangeException {

		HashMapWithList<ModelKey, LinksServiceModelRequest<T>> map = new HashMapWithList<ModelKey, LinksServiceModelRequest<T>>();
		Set<T> models = new HashSet<T>();

		for (LinksServiceModelRequest<T> request : requests) {
			Collection<T> targets = request.getTargets();
			Collection<ModelKey> targetKeys = ModelKey.readModelKeys(targets);

			for (ModelKey targetKey : targetKeys) {
				map.add(targetKey, request);
			}

			models.addAll(targets);
		}

		List<LinksPair<T>> finalPairs = this.makeFinalPairs(map, models);
		return finalPairs;
	}

	/**
	 * Large function that combines similar requests into one single request.
	 *
	 * The result is that several of the same request would be combined into one request.
	 *
	 * @param map
	 * @param models
	 * @return List of LinksPair objects corresponding to the input component requests.
	 */
	private List<LinksPair<T>> makeFinalPairs(HashMapWithList<ModelKey, ? extends LinksServiceRequest> map,
	                                             Collection<T> models) {
		List<LinksPair<T>> finalPairs = new ArrayList<LinksPair<T>>();

		for (T model : models) {
			ModelKey key = model.getModelKey();
			List<? extends LinksServiceRequest> modelRequests = map.getObjects(key);
			List<LinksPair<T>> pairsForModel = this.makePairsForModel(model, modelRequests);
			finalPairs.addAll(pairsForModel);
		}

		return finalPairs;
	}

	private List<LinksPair<T>> makePairsForModel(T model,
	                                             List<? extends LinksServiceRequest> requests) {

		List<LinksPair<T>> pairs = new ArrayList<LinksPair<T>>();
		HashMapWithList<LinksAction, LinksServiceRequest> actionMap = new HashMapWithList<LinksAction, LinksServiceRequest>();

		for (LinksServiceRequest request : requests) {
			LinksAction action = request.getAction();
			actionMap.add(action, request);
		}

		Set<LinksAction> actions = actionMap.getKeySet();
		for (LinksAction action : actions) {
			List<LinksServiceRequest> requestsOfSameAction = actionMap.getObjects(action);
			HashMapWithList<String, LinksServiceRequest> typeMap = new HashMapWithList<String, LinksServiceRequest>();

			for (LinksServiceRequest request : requestsOfSameAction) {
				String type = request.getType();
				typeMap.add(type, request);
			}

			Set<String> types = typeMap.getKeySet();
			for (String type : types) {
				List<LinksServiceRequest> similarRequests = typeMap.getObjects(type);

				Set<ModelKey> linkKeys = new HashSet<ModelKey>();
				for (LinksServiceRequest similarRequest : similarRequests) {
					Collection<ModelKey> links = similarRequest.getLinkKeys();
					linkKeys.addAll(links);
				}

				LinksPair<T> pair = new LinksPair<T>(model, linkKeys, type, action);
				pairs.add(pair);
			}
		}

		return pairs;
	}

}
