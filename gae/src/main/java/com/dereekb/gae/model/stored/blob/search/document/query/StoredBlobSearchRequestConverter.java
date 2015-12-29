package com.dereekb.gae.model.stored.blob.search.document.query;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequestConverter;
import com.dereekb.gae.model.stored.blob.search.document.index.StoredBlobDocumentBuilderStep;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.ExpressionStart;

public class StoredBlobSearchRequestConverter extends AbstractModelDocumentRequestConverter<StoredBlobSearchRequest> {

	private String nameField = StoredBlobDocumentBuilderStep.NAME_FIELD;
	private String endingField = StoredBlobDocumentBuilderStep.ENDING_FIELD;
	private String typeField = StoredBlobDocumentBuilderStep.TYPE_FIELD;
	private String dateField = ModelDocumentBuilderUtility.DATE_FIELD;
	private String descriptorIdField = ModelDocumentBuilderUtility.DESCRIPTOR_ID_FIELD;
	private String descriptorTypeField = ModelDocumentBuilderUtility.DESCRIPTOR_TYPE_FIELD;

	public StoredBlobSearchRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	public String getDateField() {
		return this.dateField;
	}

	public void setDateField(String dateField) {
		this.dateField = dateField;
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

	@Override
	public ExpressionBuilder buildExpression(StoredBlobSearchRequest request) {
		ExpressionBuilder builder = new ExpressionStart();

		if (request.getName() != null) {
			builder = builder.and(new AtomField(this.nameField, request.getName()));
		}

		if (request.getEnding() != null) {
			builder = builder.and(new AtomField(this.endingField, request.getEnding()));
		} else if (request.getType() != null) {
			builder = builder.and(new AtomField(this.typeField, request.getType()));
		}

		if (request.getDate() != null) {
			builder = builder.and(request.getDate().make(this.dateField));
		}

		if (request.getDescriptor() != null) {
			builder = builder.and(request.getDescriptor().make(this.descriptorTypeField, this.descriptorIdField));
		}

		return builder;
	}

}
