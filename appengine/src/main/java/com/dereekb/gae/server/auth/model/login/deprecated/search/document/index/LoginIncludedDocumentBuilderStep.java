package com.dereekb.gae.server.auth.model.login.search.document.index;

import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.staged.step.derivative.IncludedDocumentBuilderStep;
import com.dereekb.gae.server.auth.model.login.Login;

/**
 * {@link IncludedDocumentBuilderStep} implementation for {@link Login} types.
 *
 * @author dereekb
 *
 */
public class LoginIncludedDocumentBuilderStep extends LoginDocumentBuilderStep {

	public LoginIncludedDocumentBuilderStep() {
		super(DERIVATIVE_FIELD_FORMAT, true);
	}

}
