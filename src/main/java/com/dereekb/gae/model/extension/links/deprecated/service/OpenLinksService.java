package com.dereekb.gae.model.extension.links.deprecated.service;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.links.deprecated.functions.LinksPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Links service that allows changing arbitrary pairs.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model Type
 */
public interface OpenLinksService<T extends UniqueModel>
        extends LinksService<T> {

	public List<T> linksChange(Collection<LinksPair<T>> pairs);

}
