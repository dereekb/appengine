package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.IncludedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.IncludedModelUnavailableException;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.ModelStagedDocumentBuilderStep;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.google.appengine.api.search.Document;

/**
 * {@link IncludedDocumentBuilder} implementation.
 *
 * @author dereekb
 *
 */
public class IncludedDocumentBuilderImpl
        implements IncludedDocumentBuilder {

	private TypeModelKeyConverter keyTypeConverter;
	private Map<String, ModelStagedDocumentBuilderStep> builders;

	public IncludedDocumentBuilderImpl() {}

	public IncludedDocumentBuilderImpl(TypeModelKeyConverter keyTypeConverter,
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
	                                     Document.Builder builder) throws IncludedModelUnavailableException {
		String type = descriptor.getDescriptorType();
		String identifier = descriptor.getDescriptorId();
		ModelKey key;

		try {
			key = this.keyTypeConverter.convertKey(type, identifier);
		} catch (IllegalArgumentException e) {
			throw new IncludedModelUnavailableException(e);
		}

		ModelStagedDocumentBuilderStep builderStep = this.builders.get(type);

		if (builderStep == null) {
			throw new IncludedModelUnavailableException("Type builder for type '" + type + "' is not available.");
		}

		builderStep.performStep(key, builder);
	}

	@Override
	public String toString() {
		return "IncludedDocumentBuilderImpl [keyTypeConverter=" + this.keyTypeConverter + ", builders="
		        + this.builders + "]";
	}

}
