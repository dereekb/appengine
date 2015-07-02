package com.thevisitcompany.gae.deprecated.model.mod.publish.functions.filters;

import com.thevisitcompany.gae.deprecated.model.mod.publish.Publishable;
import com.thevisitcompany.gae.utilities.filters.FilterResult;
import com.thevisitcompany.gae.utilities.function.staged.filter.AbstractStagedFunctionFilter;

/**
 * Filter that is used for filter {@link KeyedPublishableModels} based on their published state. Can filter inclusively or exclusively.
 * 
 * Objects that are null are passed, as this filter is not equiped to handle those cases.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public class IsPublishedFilter<T extends Publishable> extends AbstractStagedFunctionFilter<T> {

	private boolean publishedState = true;

	public IsPublishedFilter() {}

	public IsPublishedFilter(boolean publishedState) {
		super();
		this.publishedState = publishedState;
	}

	@Override
	public FilterResult filterObject(T object) {
		boolean isState = true;

		if (object != null) {
			Boolean isPublished = object.isPublished();

			if (isPublished == null) {
				isPublished = false;
			}

			isState = (isPublished == publishedState);
		}

		return FilterResult.withBoolean(isState);
	}

	public boolean isPublishedState() {
		return publishedState;
	}

	public void setPublishedState(boolean publishedState) {
		this.publishedState = publishedState;
	}

}
