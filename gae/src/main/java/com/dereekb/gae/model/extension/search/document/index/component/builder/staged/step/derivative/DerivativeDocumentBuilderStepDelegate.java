package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;

import java.util.List;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.google.appengine.repackaged.com.google.protobuf.Descriptors;

/**
 * Used by {@link DerivativeDocumentBuilderStep} to retrieve a list of
 * {@link Descriptors} to describe all related components that should be
 * indexed.
 *
 * @author dereekb
 *
 */
public interface DerivativeDocumentBuilderStepDelegate<T> {

	/**
	 * Retrieves a list of {@link Descriptor} instances for each derivative type
	 * that applies to the input model.
	 *
	 * @param model
	 * @return {@link List} of {@link Descriptor} instances. Never {@code null}.
	 */
	public List<Descriptor> getDerivativeDescriptors(T model);

}
