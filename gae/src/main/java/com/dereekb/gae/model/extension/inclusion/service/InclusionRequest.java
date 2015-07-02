package com.dereekb.gae.model.extension.inclusion.service;

import java.util.Collection;
import java.util.Set;

/**
 * {@link InclusionService} request
 *
 * @author dereekb
 *
 * @param <T>
 *            Primary type
 */
public interface InclusionRequest<T> {

	/**
	 * Optional filter of the types that should be retrieved.
	 *
	 * @return {@link Set} of elements to retrieve. Null if all types should be
	 *         retrieved. The {@link InclusionService} may choose to filter out
	 *         more, so types requested through this filter are not guaranteed
	 *         to be in the final.
	 */
	public Set<String> getTypeFilter();

	/**
	 * @return {@link Collection} of models to read related elements from.
	 */
	public Collection<T> getTargets();

}
