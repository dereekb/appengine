package com.dereekb.gae.server.auth.model.login.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.AbstractIncludableDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.server.auth.model.login.Login;
import com.google.appengine.api.search.Document.Builder;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for adding
 * {@link LoginDocumentBuilderStep}.
 *
 * @author dereekb
 */
public class LoginDocumentBuilderStep extends AbstractIncludableDocumentBuilderStep<Login> {

	public static final String DERIVATIVE_PREFIX = "LN_";
	public static final String DERIVATIVE_FIELD_FORMAT = DERIVATIVE_PREFIX + "%s";

	public LoginDocumentBuilderStep() {
		super();
	}

	public LoginDocumentBuilderStep(String format, boolean inclusionStep) {
		super(format, inclusionStep);
	}

	// MARK: StagedDocumentBuilderStep
	@Override
	protected void performSharedStep(Login model,
	                                 Builder builder) {

		Date date = null;

		if (model != null) {
			date = model.getDate();
		}

		// Creation Date
		ModelDocumentBuilderUtility.addDate(this.format, date, builder);

		// TODO: Add roles
	}

	@Override
	protected void performModelStep(Login model,
	                                Builder builder) {

	}

	@Override
    public String toString() {
		return "LoginDocumentBuilderStep [format=" + this.format + ", inclusionStep=" + this.inclusionStep + "]";
    }

}
