package com.dereekb.gae.model.stored.image.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequestConverter;
import com.dereekb.gae.model.stored.image.search.document.index.StoredImageDocumentBuilderStep;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.ExpressionStart;


public class StoredImageSearchRequestConverter extends AbstractModelDocumentRequestConverter<StoredImageSearchRequest> {

	private String nameField = StoredImageDocumentBuilderStep.NAME_FIELD;
	private String summaryField = StoredImageDocumentBuilderStep.SUMMARY_FIELD;
	private String typeField = StoredImageDocumentBuilderStep.TYPE_FIELD;
	private String tagsField = StoredImageDocumentBuilderStep.TYPE_FIELD;

	public StoredImageSearchRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	public String getNameField() {
		return this.nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public String getSummaryField() {
		return this.summaryField;
	}

	public void setSummaryField(String summaryField) {
		this.summaryField = summaryField;
	}

	public String getTypeField() {
		return this.typeField;
	}

	public void setTypeField(String typeField) {
		this.typeField = typeField;
	}

	public String getTagsField() {
		return this.tagsField;
	}

	public void setTagsField(String tagsField) {
		this.tagsField = tagsField;
	}

	@Override
	public ExpressionBuilder buildExpression(StoredImageSearchRequest request) {
		ExpressionBuilder builder = new ExpressionStart();

		if (request.getName() != null) {
			builder = builder.and(new AtomField(this.nameField, request.getName()));
		}

		if (request.getSummary() != null) {
			builder = builder.and(new AtomField(this.summaryField, request.getSummary()));
		}

		if (request.getType() != null) {
			builder = builder.and(new AtomField(this.typeField, request.getType()));
		}

		if (request.getTags() != null) {
			builder = builder.and(new AtomField(this.tagsField, request.getTags()));
		}

		if (request.getStoredBlobSearch() != null) {
			builder = builder.and(request.getStoredBlobSearch().makeExpression());
		}

		if (request.getGeoPlaceSearch() != null) {
			builder = builder.and(request.getGeoPlaceSearch().makeExpression());
		}

		return builder;
	}

}
