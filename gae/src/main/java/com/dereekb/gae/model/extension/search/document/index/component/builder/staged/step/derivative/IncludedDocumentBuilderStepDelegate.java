package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;

import java.util.List;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.google.appengine.repackaged.com.google.protobuf.Descriptors;

/**
 * Used by {@link IncludedDocumentBuilderStep} to retrieve a list of
 * {@link Descriptors} to describe all related components that should be indexed
 * as a part of the descriptor.
 *
 * @author dereekb
 *
 */
public interface IncludedDocumentBuilderStepDelegate<T> {

	/**
	 * Retrieves a list of {@link Descriptor} instances for each included type
	 * that applies to the input model.
	 *
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@link List} of {@link Descriptor} instances. Never {@code null}.
	 */
	public List<Descriptor> getIncludedModelDescriptors(T model);

}
