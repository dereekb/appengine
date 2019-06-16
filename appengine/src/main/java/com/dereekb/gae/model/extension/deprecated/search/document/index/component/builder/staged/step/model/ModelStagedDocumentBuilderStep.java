package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.google.appengine.api.search.Document;

/**
 *
 * {@link StagedDocumentBuilderStep} that can apply values to a
 * {@link Document.Builder} from a model corresponding to a {@link ModelKey}.
 *
 * @author dereekb
 *
 */
public interface ModelStagedDocumentBuilderStep {

	public void performStep(ModelKey key,
	                        Document.Builder builder) throws UnavailableModelException;

}
