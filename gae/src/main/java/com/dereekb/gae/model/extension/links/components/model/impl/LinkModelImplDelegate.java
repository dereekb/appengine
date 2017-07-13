package com.dereekb.gae.model.extension.links.components.model.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.links.components.Link;

/**
 * {@link LinkModelImpl} delegate.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public interface LinkModelImplDelegate<T> {

	/**
	 * The link type.
	 *
	 * @return
	 */
	public String getLinkModelType();

	/**
	 * Generates a map of all supported links.
	 *
	 * @param model
	 * @return map keyed by link type to all {@link Link} types.
	 */
	public Map<String, Link> buildLinks(T model);

}
