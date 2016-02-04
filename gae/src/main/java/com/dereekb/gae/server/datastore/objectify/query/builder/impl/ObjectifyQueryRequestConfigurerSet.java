package com.dereekb.gae.server.datastore.objectify.query.builder.impl;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestConfigurer;

/**
 * {@link ObjectifyQueryRequestConfigurer} implementation that wraps several
 * other steps.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyQueryRequestConfigurerSet<T extends ObjectifyModel<T>>
        implements ObjectifyQueryRequestConfigurer<T> {

	private Iterable<ObjectifyQueryRequestConfigurer<T>> steps;

	public Iterable<ObjectifyQueryRequestConfigurer<T>> getSteps() {
		return this.steps;
	}

	public void setSteps(Iterable<ObjectifyQueryRequestConfigurer<T>> steps) {
		this.steps = steps;
	}

	@Override
	public void configure(ObjectifyQueryRequest<T> request) {
		for (ObjectifyQueryRequestConfigurer<T> step : this.steps) {
			step.configure(request);
		}
	}

	@Override
	public String toString() {
		return "ObjectifyQueryRequestConfigurerSet [steps=" + this.steps + "]";
	}

}
