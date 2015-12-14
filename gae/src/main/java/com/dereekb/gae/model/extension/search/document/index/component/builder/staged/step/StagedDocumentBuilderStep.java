package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.google.appengine.api.search.Document;

/**
 * Step in a {@link StagedDocumentBuilder}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface StagedDocumentBuilderStep<T extends UniqueSearchModel> {

	/**
	 * Updates the {@link Document.Builder} with the input model.
	 *
	 * @param model
	 *            Model to use. Can by {@code null} if the implementation allows
	 *            him.
	 * @param builder
	 *            {@link Document.Builder}. Never {@code null}.
	 * @throws NullPointerException
	 *             thrown if the implementation does not allow the model to be
	 *             {@code null}.
	 */
	public void performStep(T model,
	                        Document.Builder builder) throws NullPointerException;

}
