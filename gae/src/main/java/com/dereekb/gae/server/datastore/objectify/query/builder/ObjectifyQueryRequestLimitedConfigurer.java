package com.dereekb.gae.server.datastore.objectify.query.builder;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;

/**
 * Acts as a step in configuring a {@link ObjectifyQueryRequestLimitedBuilder}.
 *
 * @author dereekb
 *
 * @see ObjectifyQueryRequestConfigurer
 */
public interface ObjectifyQueryRequestLimitedConfigurer {

	/**
	 * Configures the input request.
	 *
	 * @param request
	 *            {@link ObjectifyQueryRequestLimitedBuilder}. Never
	 *            {@code null}.
	 */
	public void configure(ObjectifyQueryRequestLimitedBuilder request);

}
