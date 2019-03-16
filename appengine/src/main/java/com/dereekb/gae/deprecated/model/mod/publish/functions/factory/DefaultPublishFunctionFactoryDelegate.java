package com.thevisitcompany.gae.deprecated.model.mod.publish.functions.factory;

import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.filters.PublishImmediateCheckFilter;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.filters.PublishRulesFilter;

public class DefaultPublishFunctionFactoryDelegate<T extends KeyedPublishableModel<Long>>
        implements PublishFunctionFactoryDelegate<T> {

	private PublishRulesFilter<T> rulesFilter;
	private PublishImmediateCheckFilter<T> immediateCheckFilter;

	@Override
	public PublishRulesFilter<T> makeRulesFilter() {
		return this.rulesFilter;
	}

	@Override
	public PublishImmediateCheckFilter<T> makeImmediateCheckFilter() {
		return this.immediateCheckFilter;
	}

	public PublishRulesFilter<T> getRulesFilter() {
		return rulesFilter;
	}

	public void setRulesFilter(PublishRulesFilter<T> rulesFilter) {
		this.rulesFilter = rulesFilter;
	}

	public PublishImmediateCheckFilter<T> getImmediateCheckFilter() {
		return immediateCheckFilter;
	}

	public void setImmediateCheckFilter(PublishImmediateCheckFilter<T> immediateCheckFilter) {
		this.immediateCheckFilter = immediateCheckFilter;
	}

}
