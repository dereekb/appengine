package com.dereekb.gae.model.geo.place.search.document.query;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequestConverter;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.ExpressionStart;


public class GeoPlaceSearchRequestConverter extends AbstractModelDocumentRequestConverter<GeoPlaceSearchRequest> {

	private String dateField = ModelDocumentBuilderUtility.DATE_FIELD;
	private String pointField = ModelDocumentBuilderUtility.POINT_FIELD;
	private String descriptorIdField = ModelDocumentBuilderUtility.DESCRIPTOR_ID_FIELD;
	private String descriptorTypeField = ModelDocumentBuilderUtility.DESCRIPTOR_TYPE_FIELD;

	public GeoPlaceSearchRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	public String getDateField() {
		return this.dateField;
	}

	public void setDateField(String dateField) {
		this.dateField = dateField;
	}

	public String getPointField() {
		return this.pointField;
	}

	public void setPointField(String pointField) {
		this.pointField = pointField;
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
	public ExpressionBuilder buildExpression(GeoPlaceSearchRequest request) {
		ExpressionBuilder builder = new ExpressionStart();

		if (request.getDate() != null) {
			builder.and(request.getDate().make(this.dateField));
		}

		if (request.getDescriptor() != null) {
			builder.and(request.getDescriptor().make(this.descriptorTypeField, this.descriptorIdField));
		}

		if (request.getPoint() != null) {
			builder.and(request.getPoint().make(this.pointField));
		}

		return builder;
	}

}
