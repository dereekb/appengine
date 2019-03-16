package com.dereekb.gae.model.extension.links.deprecated.functions;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;

/**
 * Links function that links two objects together using the specified handler class and
 *
 * @author dereekb
 *
 * @param <T>
 */
public class LinksFunction<T extends UniqueModel> extends FilteredStagedFunction<T, LinksPair<T>>
        implements LinksHandler<T> {

	private LinksHandler<T> handler = this;
	private LinksFunctionComponent<T> component;

	public LinksFunction() {
		super();
	}

	public LinksFunction(LinksHandler<T> handler) {
		this.setHandler(handler);
	}

	@Override
	protected void doFunction() {
		Iterable<LinksPair<T>> pairs = this.getWorkingObjects();
		this.link(pairs);
	}

	protected void link(Iterable<LinksPair<T>> pairs) {
		for (LinksPair<T> pair : pairs) {
			boolean success = this.component.link(pair);
			pair.setSuccessful(success);
		}
	}

	public LinksHandler<T> getHandler() {
		return this.handler;
	}

	public void setHandler(LinksHandler<T> handler) throws IllegalArgumentException {
		this.handler = handler;
		this.component = new LinksFunctionComponent<T>(handler);
	}

}
