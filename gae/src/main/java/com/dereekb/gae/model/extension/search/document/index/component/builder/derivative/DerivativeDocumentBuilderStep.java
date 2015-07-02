package com.dereekb.gae.model.extension.search.document.index.component.builder.derivative;

import com.google.appengine.api.search.Document.Builder;

public interface DerivativeDocumentBuilderStep<T> {

	/**
	 * Updates the input {@link Builder} using the input identifier to
	 * potentially retrieve another model.
	 *
	 * @param identifier
	 *            Identifier of the derivative model.
	 * @param builder
	 *            Builder to update.
	 */
	public void updateBuilder(String identifier,
	                          Builder builder);

}
