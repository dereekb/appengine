package com.dereekb.gae.model.extension.inclusion.service;

import java.util.Collection;

/**
 * Pre-configured implementaiton of {@link InclusionService} that builds the
 * {@link InclusionRequest} from input.
 *
 * @author dereekb
 *
 */
public interface ConfiguredInclusionService<T> {

	public InclusionResponse<T> loadRelated(Collection<T> models);

}
