package com.dereekb.gae.model.extension.links.components.accessor;

import java.util.List;

import com.dereekb.gae.model.extension.links.deprecated.components.Link;

/**
 * Used for reading {@link Link} values from the input model.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
@Deprecated
public interface LinkAccessor<T>
        extends LinkReader<T> {

	@Override
	public List<? extends Link> getLinks(T model);

}
