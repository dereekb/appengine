package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.google.appengine.api.search.Document;

/**
 * Used by {@link IncludedDocumentBuilderStep} to add data from models that
 * are referenced by a {@link Descriptor} to the {@link Document.Builder}
 * instance.
 *
 * @author dereekb
 *
 */
public interface IncludedDocumentBuilder {

	/**
	 * Applys the derivative's search elements to the input builder.
	 *
	 * @param descriptor
	 *            {@link Descriptor}. Never {@code null}.
	 * @param builder
	 *            {@link Document.Builder}. Never {@code null}.
	 * @throws IncludedModelUnavailableException
	 *             thrown if the derivative specified is not available.
	 */
	public void applyDerivativeComponent(Descriptor descriptor,
	                                     Document.Builder builder) throws IncludedModelUnavailableException;

}
