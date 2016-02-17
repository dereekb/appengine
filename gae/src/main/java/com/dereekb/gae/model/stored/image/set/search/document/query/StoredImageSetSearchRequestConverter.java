package com.dereekb.gae.model.stored.image.set.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequestConverter;
import com.dereekb.gae.model.stored.image.set.search.document.index.StoredImageSetDocumentBuilderStep;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.ExpressionStart;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.TextField;

public class StoredImageSetSearchRequestConverter extends AbstractModelDocumentRequestConverter<StoredImageSetSearchRequest> {

	private String labelField = StoredImageSetDocumentBuilderStep.LABEL_FIELD;
	private String detailField = StoredImageSetDocumentBuilderStep.DETAIL_FIELD;
	private String tagsField = StoredImageSetDocumentBuilderStep.TAGS_FIELD;

	public StoredImageSetSearchRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	@Override
	public ExpressionBuilder buildExpression(StoredImageSetSearchRequest request) {
		ExpressionBuilder builder = new ExpressionStart();

		if (request.getLabel() != null) {
			builder = builder.and(new AtomField(this.labelField, request.getLabel()));
		}

		if (request.getDetail() != null) {
			builder = builder.and(new TextField(this.detailField, request.getDetail()));
		}

		if (request.getTags() != null) {
			builder = builder.and(new TextField(this.tagsField, request.getTags()));
		}

		return builder;
	}

}
