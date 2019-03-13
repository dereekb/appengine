package com.dereekb.gae.model.extension.links.deprecated.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.AtomicReadService;
import com.dereekb.gae.model.extension.links.deprecated.functions.BidirectionalLinkerFunction;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Component for running links-related changes between two types of objects.
 *
 * Provides a layer of abstraction of not needing to know the other type.
 *
 * @author dereekb
 *
 * @param <T>
 *            Target Type
 */
public class BidirectionalLinkServiceComponent<T extends UniqueModel> extends LinksServiceComponent<T> {

	private final BidirectionalLinkerFunction<T, ?> linksFunction;

	public BidirectionalLinkServiceComponent(AtomicReadService<T> readService,
	        BidirectionalLinkerFunction<T, ?> linksFunction) {
		super(readService, null);
		this.linksFunction = linksFunction;
	}

	@Override
    public List<T> linksChange(Collection<LinksPair<T>> pairs) {
		this.linksFunction.addObjects(pairs);
		boolean success = this.linksFunction.run();

		List<T> changed = Collections.emptyList();

		if (success) {
			changed = this.getSuccessfulItems(pairs);
		}

		return changed;
	}

}
