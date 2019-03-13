package com.dereekb.gae.web.api.util.attribute;

import java.util.Collection;

/**
 * Collection of {@link KeyedInvalidAttributes} values.
 *
 * @author dereekb
 *
 */
public interface MultiKeyedInvalidAttributes {

	/**
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<? extends KeyedInvalidAttribute> getInvalidAttributes();

}
