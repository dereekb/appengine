package com.dereekb.gae.model.extension.search.document.search.service.model.impl;

import com.dereekb.gae.model.extension.search.document.search.service.impl.DocumentSearchRequestOptionsImpl;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;


public abstract class AbstractModelDocumentRequest extends DocumentSearchRequestOptionsImpl {

	private ExpressionBuilder override;

	public ExpressionBuilder getOverride() {
		return this.override;
	}

	public void setOverride(ExpressionBuilder override) {
		this.override = override;
	}

}
