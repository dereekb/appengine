package com.dereekb.gae.model.extension.links.components.accessor;

import java.util.List;

import com.dereekb.gae.model.extension.links.components.Link;

/**
 * Used for reading {@link Link} values from the input model.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface LinkAccessor<T>
        extends LinkReader<T> {

	public List<? extends Link> getLinks(T model);

}
