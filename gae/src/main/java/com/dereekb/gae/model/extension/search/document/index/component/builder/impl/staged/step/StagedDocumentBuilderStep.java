package com.dereekb.gae.model.extension.search.document.index.component.builder.impl.staged.step;

import com.dereekb.gae.model.extension.search.document.index.component.builder.impl.staged.StagedDocumentBuilder;
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

	public void performStep(T model,
	                        Document.Builder builder);

}
