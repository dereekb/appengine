package com.dereekb.gae.model.extension.search.document.search.service.model.impl;

import com.dereekb.gae.model.extension.search.document.search.service.impl.DocumentSearchRequestOptionsImpl;
import com.dereekb.gae.server.search.document.query.expression.Expression;


public abstract class AbstractModelDocumentRequest extends DocumentSearchRequestOptionsImpl {

	private Expression override;

	public Expression getOverride() {
		return this.override;
	}

	public void setOverride(Expression override) {
		this.override = override;
	}

}
