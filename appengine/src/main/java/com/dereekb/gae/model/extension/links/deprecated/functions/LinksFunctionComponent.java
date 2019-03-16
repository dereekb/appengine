package com.dereekb.gae.model.extension.links.deprecated.functions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.dereekb.gae.model.extension.links.deprecated.exception.UnsupportedLinkChangeException;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Component that "links" objects together using a delegate {@link LinksHandler} class that does the actual linking.
 *
 * @author dereekb
 * @see {@link LinksFunction} for usage.
 */
public class LinksFunctionComponent<T extends UniqueModel> {

	private LinksMethodRetriever retriever;
	private final LinksHandler<T> handler;

	public LinksFunctionComponent(LinksHandler<T> handler) throws IllegalArgumentException {
		if (handler == null) {
			throw new IllegalArgumentException("LinksHandler cannot be null.");
		}

		this.handler = handler;
		this.retriever = new LinksMethodRetriever(handler.getClass());
	}

	protected boolean link(LinksPair<T> pair) {
		boolean success = false;

		String name = pair.getName();
		LinksAction operation = pair.getOperation();
		Method method = this.retriever.retrieveMethod(name, operation);

		if (method == null) {
			throw UnsupportedLinkChangeException.ExceptionWithType(name, operation);
		} else {
			try {
				method.invoke(this.handler, pair);
				success = true;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				success = false;
			}
		}

		return success;
	}

	public LinksMethodRetriever getRetriever() {
		return this.retriever;
	}

	public void setRetriever(LinksMethodRetriever retriever) {
		this.retriever = retriever;
	}

	public LinksHandler<T> getHandler() {
		return this.handler;
	}

}
