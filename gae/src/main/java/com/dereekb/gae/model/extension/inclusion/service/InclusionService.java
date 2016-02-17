package com.dereekb.gae.model.extension.inclusion.service;


/**
 * Services for retrieving related models from an input model.
 *
 * @author dereekb
 *
 * @param <T>
 *            Primary type
 */
public interface InclusionService<T> {

	/**
	 * Loads all related values.
	 *
	 * @param request
	 *            {@link InclusionRequest}. Never {@code null}.
	 * @return {@link InclusionResponse} for the {@link InclusionRequest}. Never
	 *         {@code null}.
	 */
	public InclusionResponse<T> loadRelated(InclusionRequest<T> request);

}
