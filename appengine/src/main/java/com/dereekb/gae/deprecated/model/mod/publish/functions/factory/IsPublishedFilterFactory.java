package com.thevisitcompany.gae.deprecated.model.mod.publish.functions.factory;

import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.filters.IsPublishedFilter;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.factory.filter.AbstractStagedFunctionFilterFactory;

public class IsPublishedFilterFactory<T extends KeyedPublishableModel<Long>> extends AbstractStagedFunctionFilterFactory<IsPublishedFilter<T>, T> {

	private boolean isPublished = true;

	public IsPublishedFilterFactory() {
		super(StagedFunctionStage.FINISHED);
	}

	@Override
	public IsPublishedFilter<T> generateFilter() {
		IsPublishedFilter<T> filter = new IsPublishedFilter<T>(isPublished);
		return filter;
	}

	public boolean isPublished() {
		return isPublished;
	}

	public void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}

}
