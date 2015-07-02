package com.thevisitcompany.gae.deprecated.model.mod.publish.functions.filters;

import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.PublishAction;

/**
 * Automatically returns true for all immediate requests.
 */
public class AllowAllPublishImmediateCheckFilterDelegate<T>
        implements AllowImmediatePublishActionDelegate<T> {

	@Override
	public boolean canDoActionImmediately(PublishAction action,
	                                      T model) {
		return true;
	}

}
