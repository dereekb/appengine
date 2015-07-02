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

	public InclusionResponse<T> loadRelated(InclusionRequest<T> request);

}
