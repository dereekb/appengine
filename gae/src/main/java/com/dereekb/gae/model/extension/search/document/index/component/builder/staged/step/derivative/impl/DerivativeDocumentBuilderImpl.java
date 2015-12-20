package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.DerivativeDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.DerivativeUnavailableException;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.ModelStagedDocumentBuilderStep;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.TypeModelKeyConverter;
import com.google.appengine.api.search.Document;

/**
 * {@link DerivativeDocumentBuilder} implementation.
 *
 * @author dereekb
 *
 */
public class DerivativeDocumentBuilderImpl
        implements DerivativeDocumentBuilder {

	private TypeModelKeyConverter keyTypeConverter;
	private Map<String, ModelStagedDocumentBuilderStep> builders;

	public DerivativeDocumentBuilderImpl() {}

	public DerivativeDocumentBuilderImpl(TypeModelKeyConverter keyTypeConverter,
	        Map<String, ModelStagedDocumentBuilderStep> builders) {
		this.keyTypeConverter = keyTypeConverter;
		this.builders = builders;
	}

	public TypeModelKeyConverter getKeyTypeConverter() {
		return this.keyTypeConverter;
	}

	public void setKeyTypeConverter(TypeModelKeyConverter keyTypeConverter) {
		this.keyTypeConverter = keyTypeConverter;
	}

	public Map<String, ModelStagedDocumentBuilderStep> getBuilders() {
		return this.builders;
	}

	public void setBuilders(Map<String, ModelStagedDocumentBuilderStep> builders) {
		this.builders = builders;
	}

	@Override
	public void applyDerivativeComponent(Descriptor descriptor,
	                                     Document.Builder builder) throws DerivativeUnavailableException {
		String type = descriptor.getDescriptorType();
		String identifier = descriptor.getDescriptorId();
		ModelKey key;

		try {
			key = this.keyTypeConverter.convertKey(type, identifier);
		} catch (IllegalArgumentException e) {
			throw new DerivativeUnavailableException(e);
		}

		ModelStagedDocumentBuilderStep builderStep = this.builders.get(type);

		if (builderStep == null) {
			throw new DerivativeUnavailableException("Type builder for type '" + type + "' is not available.");
		}

		builderStep.performStep(key, builder);
	}

	@Override
	public String toString() {
		return "DerivativeDocumentBuilderImpl [keyTypeConverter=" + this.keyTypeConverter + ", builders="
		        + this.builders + "]";
	}

}
