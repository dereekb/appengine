package com.dereekb.gae.model.extension.links.deprecated.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.deprecated.service.BidirectionalLinkServiceComponent;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;

/**
 * Function for linking or unlinking two sets of objects together.
 *
 * @author dereekb
 *
 * @param <T>
 *            Primary Target Type
 * @param <S>
 *            Secondary Target Type
 *
 * @see {@link BidirectionalLinkServiceComponent} for implementation.
 */
public class BidirectionalLinkerFunction<T extends UniqueModel, S extends UniqueModel> extends FilteredStagedFunction<T, LinksPair<T>> {

	/**
	 * Whether or not to require loading all items.
	 *
	 * If an item is not found, an exception is thrown if safe is true.
	 */
	private boolean safe = false;

	private LinksHandler<T> primaryHandler;
	private LinksHandler<S> secondaryHandler;

	protected LinksFunctionComponent<T> primaryComponent;
	protected LinksFunctionComponent<S> secondaryComponent;

	private BidirectionalLinkerFunctionTypeDelegate typeDelegate;

	private Getter<S> secondaryGetter;
	private ConfiguredSetter<S> secondarySetter;

	@Override
	protected void doFunction() {
		Iterable<LinksPair<T>> pairs = this.getWorkingObjects();
		this.link(pairs);
	}

	protected void link(Iterable<LinksPair<T>> pairs) {
		HashMapWithSet<ModelKey, LinksPair<T>> mapping = this.buildSecondaryMappingSet(pairs);

		Set<ModelKey> secondaryIds = mapping.keySet();
		List<S> secondaryObjects = this.secondaryGetter.getWithKeys(secondaryIds);

		if (this.safe && (secondaryIds.size() != secondaryObjects.size())) {
			throw new RuntimeException("Number of objects loaded was not equal to the number of keys.");
			// throw new UnavailableObjectsException(UnavailableReason.MISSING);
		}

		List<LinksPair<S>> secondaryPairs = this.buildSecondaryLinksPairs(mapping, secondaryObjects);

		this.link(pairs, this.primaryComponent);
		this.link(secondaryPairs, this.secondaryComponent);

		this.secondarySetter.save(secondaryObjects);
	}

	protected <L extends UniqueModel> void link(Iterable<LinksPair<L>> pairs,
	                                            LinksFunctionComponent<L> component) {
		for (LinksPair<L> pair : pairs) {
			boolean success = component.link(pair);
			pair.setSuccessful(success);
		}
	}

	protected HashMapWithSet<ModelKey, LinksPair<T>> buildSecondaryMappingSet(Iterable<LinksPair<T>> pairs) {
		HashMapWithSet<ModelKey, LinksPair<T>> mapping = new HashMapWithSet<ModelKey, LinksPair<T>>();

		for (LinksPair<T> pair : pairs) {
			Collection<ModelKey> links = pair.getLinks();
			mapping.addAll(links, pair);
		}

		return mapping;
	}

	protected List<LinksPair<S>> buildSecondaryLinksPairs(HashMapWithSet<ModelKey, LinksPair<T>> mapping,
	                                                      List<S> objects) {
		List<LinksPair<S>> pairs = new ArrayList<LinksPair<S>>();

		for (S object : objects) {
			ModelKey key = object.getModelKey();
			List<LinksPair<T>> primaryPairs = mapping.getObjects(key);
			List<LinksPair<S>> newPairs = this.buildSecondaryLinksPair(object, primaryPairs);
			pairs.addAll(newPairs);
		}

		return pairs;
	}

	protected List<LinksPair<S>> buildSecondaryLinksPair(S object,
	                                                     List<LinksPair<T>> primaryPairs) {
		List<LinksPair<S>> newPairs = new ArrayList<LinksPair<S>>();
		HashMapWithList<LinksAction, LinksPair<T>> actionsMap = new HashMapWithList<LinksAction, LinksPair<T>>();

		for (LinksPair<T> primaryPair : primaryPairs) {
			LinksAction action = primaryPair.getOperation();
			actionsMap.add(action, primaryPair);
		}

		Set<LinksAction> actions = actionsMap.keySet();
		for (LinksAction action : actions) {
			List<LinksPair<T>> pairsForAction = actionsMap.get(action);
			HashMapWithList<String, LinksPair<T>> typeMap = new HashMapWithList<String, LinksPair<T>>();

			for (LinksPair<T> pairForAction : pairsForAction) {
				String type = pairForAction.getName();
				typeMap.add(type, pairForAction);
			}

			Set<String> types = typeMap.keySet();
			for (String type : types) {
				List<LinksPair<T>> similarRequests = typeMap.getObjects(type);
				String secondaryPairingType = this.typeDelegate.linkerTypeForType(type);

				Set<ModelKey> linkKeys = new HashSet<ModelKey>();
				for (LinksPair<T> similarRequest : similarRequests) {
					T target = similarRequest.getTarget();
					ModelKey targetKeys = target.getModelKey();
					linkKeys.add(targetKeys);
				}

				LinksPair<S> newPair = new LinksPair<S>(object, linkKeys, secondaryPairingType, action);
				newPairs.add(newPair);
			}
		}

		return newPairs;
	}

	public LinksHandler<T> getPrimaryHandler() {
		return this.primaryHandler;
	}

	public void setPrimaryHandler(LinksHandler<T> primaryHandler) throws IllegalArgumentException {
		this.primaryHandler = primaryHandler;
		this.primaryComponent = new LinksFunctionComponent<T>(primaryHandler);
	}

	public LinksHandler<S> getSecondaryHandler() {
		return this.secondaryHandler;
	}

	public void setSecondaryHandler(LinksHandler<S> secondaryHandler) throws IllegalArgumentException {
		this.secondaryHandler = secondaryHandler;
		this.secondaryComponent = new LinksFunctionComponent<S>(secondaryHandler);
	}

	public Getter<S> getSecondaryGetter() {
		return this.secondaryGetter;
	}

	public void setSecondaryGetter(Getter<S> secondaryGetter) {
		this.secondaryGetter = secondaryGetter;
	}

	public ConfiguredSetter<S> getSecondarySetter() {
		return this.secondarySetter;
	}

	public void setSecondarySetter(ConfiguredSetter<S> secondarySetter) {
		this.secondarySetter = secondarySetter;
	}

	public BidirectionalLinkerFunctionTypeDelegate getTypeDelegate() {
		return this.typeDelegate;
	}

	public void setTypeDelegate(BidirectionalLinkerFunctionTypeDelegate typeDelegate) {
		this.typeDelegate = typeDelegate;
	}

	public boolean isSafe() {
		return this.safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}

}
