package com.dereekb.gae.model.extension.search.document.index.component.builder;

import com.dereekb.gae.server.search.UniqueSearchModel;
import com.google.appengine.api.search.Document;

/**
 * Step in a {@link StagedDocumentBuilder}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface StagedDocumentBuilderStep<T extends UniqueSearchModel> {

	public void updateBuilder(T model,
	                          Document.Builder builder);

}
