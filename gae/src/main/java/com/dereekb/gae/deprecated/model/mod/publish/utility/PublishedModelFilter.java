package com.thevisitcompany.gae.deprecated.model.mod.publish.utility;

import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.utilities.filters.AbstractFilter;
import com.thevisitcompany.gae.utilities.filters.FilterResult;

public class PublishedModelFilter<T extends KeyedPublishableModel<Long>> extends AbstractFilter<T> {

	private boolean publishedState = true;

	public PublishedModelFilter() {
		super();
	}

	public PublishedModelFilter(boolean publishedState) {
		super();
		this.publishedState = publishedState;
	}

	public boolean isPublishedState() {
		return publishedState;
	}

	public void setPublishedState(boolean publishedState) {
		this.publishedState = publishedState;
	}

	@Override
	public FilterResult filterObject(T object) {
		boolean isState = (object.isPublished() == publishedState);
		return FilterResult.withBoolean(isState);
	}

}
