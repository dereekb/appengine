package com.dereekb.gae.model.extension.links.components.accessor;

import java.util.List;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.ReadOnlyLink;

/**
 * Used for reading {@link Link} values from the input model.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface LinkReader<T> {

	public List<? extends ReadOnlyLink> getLinks(T model);

}
