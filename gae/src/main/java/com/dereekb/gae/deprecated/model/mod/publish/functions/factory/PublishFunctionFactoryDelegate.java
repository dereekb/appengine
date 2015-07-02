package com.thevisitcompany.gae.deprecated.model.mod.publish.functions.factory;

import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.filters.PublishImmediateCheckFilter;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.filters.PublishRulesFilter;

public interface PublishFunctionFactoryDelegate<T extends KeyedPublishableModel<Long>> {

	public PublishRulesFilter<T> makeRulesFilter();

	public PublishImmediateCheckFilter<T> makeImmediateCheckFilter();

}
