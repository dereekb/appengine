package com.thevisitcompany.gae.deprecated.authentication.login.functions.factory;

import com.thevisitcompany.gae.deprecated.authentication.login.functions.filters.HasLoginFilter;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.factory.filter.AbstractStagedFunctionFilterFactory;

public class HasLoginFunctionHandlerFilterFactory<T> extends AbstractStagedFunctionFilterFactory<HasLoginFilter<T>, T> {

	public HasLoginFunctionHandlerFilterFactory() {
		super(StagedFunctionStage.STARTED);
	}

	@Override
	public HasLoginFilter<T> generateFilter() {
		HasLoginFilter<T> filter = new HasLoginFilter<T>();
		return filter;
	}

}
