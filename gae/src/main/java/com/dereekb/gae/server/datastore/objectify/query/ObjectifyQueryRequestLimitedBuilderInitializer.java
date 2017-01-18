package com.dereekb.gae.server.datastore.objectify.query;

import java.util.Map;

/**
 * Used to initialize an {@link ObjectifyQueryRequestLimitedBuilder} instance.
 *
 * @author dereekb
 *
 */
public interface ObjectifyQueryRequestLimitedBuilderInitializer {

	/**
	 * Initializes the input builder using the provided parameters.
	 *
	 * @param builder
	 *            {@link ObjectifyQueryRequestLimitedBuilder} to update. Never
	 *            {@code null}.
	 * @param parameters
	 *            {@link Map} of optional parameters. Never {@code null}.
	 */
	public void initalizeBuilder(ObjectifyQueryRequestLimitedBuilder builder,
	                             Map<String, String> parameters);

	/**
	 * Overrides the input options.
	 * 
	 * @param builder
	 *            {@link ObjectifyQueryRequestLimitedBuilder} to update. Never
	 *            {@code null}.
	 * @param options
	 *            {@link ObjectifyQueryRequestOptions}. Never {@code null}.
	 */
	public void overrideOptions(ObjectifyQueryRequestLimitedBuilder builder,
	                            ObjectifyQueryRequestOptions options);

}
