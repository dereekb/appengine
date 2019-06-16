package com.dereekb.gae.model.extension.search.document.search.service.model.impl;

import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.impl.field.ExpressionStart;

public class AbstractDescribedModelDocumentRequestConverter<T extends AbstractDescribedModelDocumentRequest> extends AbstractModelDocumentRequestConverter<T> {

	private String descriptorIdField = ModelDocumentBuilderUtility.DESCRIPTOR_ID_FIELD;
	private String descriptorTypeField = ModelDocumentBuilderUtility.DESCRIPTOR_TYPE_FIELD;

	public AbstractDescribedModelDocumentRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	public String getDescriptorIdField() {
		return this.descriptorIdField;
	}

	public void setDescriptorIdField(String descriptorIdField) {
		this.descriptorIdField = descriptorIdField;
	}

	public String getDescriptorTypeField() {
		return this.descriptorTypeField;
	}

	public void setDescriptorTypeField(String descriptorTypeField) {
		this.descriptorTypeField = descriptorTypeField;
	}

	// MARK: AbstractDescribedModelDocumentRequest
	@Override
	public ExpressionBuilder buildExpression(T request) {
		ExpressionBuilder builder = new ExpressionStart();

		if (request.getDescriptor() != null) {
			builder = builder.and(request.getDescriptor().make(this.descriptorTypeField, this.descriptorIdField));
		}

		return builder;
    }

	@Override
	public String toString() {
		return "AbstractDescribedModelDocumentRequestConverter [descriptorIdField=" + this.descriptorIdField
		        + ", descriptorTypeField=" + this.descriptorTypeField + "]";
	}

}
