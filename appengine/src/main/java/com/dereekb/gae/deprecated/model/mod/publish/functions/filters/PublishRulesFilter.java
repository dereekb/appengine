package com.thevisitcompany.gae.deprecated.model.mod.publish.functions.filters;

import java.util.ArrayList;
import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.PublishAction;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.PublishPair;
import com.thevisitcompany.gae.deprecated.model.mod.publish.utility.PublishRulesDelegate;
import com.thevisitcompany.gae.utilities.collections.map.HashMapWithList;
import com.thevisitcompany.gae.utilities.filters.FilterResult;
import com.thevisitcompany.gae.utilities.filters.FilterResults;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionObjectFilter;

/**
 * Abstract filter for Publishing functions. Uses a publish rules delegate to filter.
 * 
 * @author dereekb
 * 
 * @param <T>
 */
public class PublishRulesFilter<T extends KeyedPublishableModel<Long>>
        implements StagedFunctionObjectFilter<T, PublishPair<T>> {

	public PublishRulesDelegate<T> delegate;

	public PublishRulesFilter() {}

	public PublishRulesFilter(PublishRulesDelegate<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public FilterResults<PublishPair<T>> filterFunctionObjects(StagedFunctionStage stage,
	                                                           Iterable<PublishPair<T>> pairs) {

		FilterResults<PublishPair<T>> results = new FilterResults<PublishPair<T>>();
		HashMapWithList<PublishAction, PublishPair<T>> map = PublishPair.getActionPairsMap(pairs);

		List<PublishPair<T>> publishRequests = new ArrayList<PublishPair<T>>();
		publishRequests.addAll(map.getObjects(PublishAction.PUBLISH_REQUEST));
		publishRequests.addAll(map.getObjects(PublishAction.PUBLISH));
		results.insert(this.filterPublishRequests(publishRequests));

		List<PublishPair<T>> unpublishRequests = new ArrayList<PublishPair<T>>();
		unpublishRequests.addAll(map.getObjects(PublishAction.UNPUBLISH_REQUEST));
		unpublishRequests.addAll(map.getObjects(PublishAction.UNPUBLISH));
		results.insert(this.filterUnpublishRequests(unpublishRequests));

		return results;
	}

	private FilterResults<PublishPair<T>> filterPublishRequests(List<PublishPair<T>> publish) {
		FilterResults<PublishPair<T>> results = new FilterResults<PublishPair<T>>();

		for (PublishPair<T> pair : publish) {
			T object = pair.getSource();
			FilterResult result = FilterResult.withBoolean(this.delegate.canPublish(object));
			results.add(result, pair);
		}

		return results;
	}

	private FilterResults<PublishPair<T>> filterUnpublishRequests(List<PublishPair<T>> unpublish) {
		FilterResults<PublishPair<T>> results = new FilterResults<PublishPair<T>>();

		for (PublishPair<T> pair : unpublish) {
			T object = pair.getSource();
			FilterResult result = FilterResult.withBoolean(this.delegate.canUnpublish(object));
			results.add(result, pair);
		}

		return results;
	}

	public PublishRulesDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(PublishRulesDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
