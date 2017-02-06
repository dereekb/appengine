package com.dereekb.gae.server.auth.model.login.search.document.index;

import java.util.Date;
import java.util.Set;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.AbstractDerivableDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.utilities.collections.set.dencoder.EncodedLongDecoder;
import com.google.appengine.api.search.Document.Builder;
import com.google.common.base.Joiner;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for adding
 * {@link LoginDocumentBuilderStep}.
 *
 * @author dereekb
 */
public class LoginDocumentBuilderStep extends AbstractDerivableDocumentBuilderStep<Login> {

	private static final String ROLES_SPLITTER = " ";

	public static final String DATE_FIELD = "date";
	public static final String ROOT_FIELD = "root";
	public static final String ROLES_FIELD = "roles";
	public static final String GROUP_FIELD = "group";

	public static final String DERIVATIVE_PREFIX = "LN_";
	public static final String DERIVATIVE_FIELD_FORMAT = DERIVATIVE_PREFIX + "%s";

	private EncodedLongDecoder<String> rolesDecoder;

	public LoginDocumentBuilderStep() {
		super();
	}

	public LoginDocumentBuilderStep(String format, boolean inclusionStep) {
		super(format, inclusionStep);
	}

	public EncodedLongDecoder<String> getRolesDecoder() {
		return this.rolesDecoder;
	}

	public void setRolesDecoder(EncodedLongDecoder<String> rolesDecoder) {
		this.rolesDecoder = rolesDecoder;
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
	}

	@Override
	protected void performDescribedModelStep(Login model,
	                                         Builder builder) {
		Long roles = model.getRoles();
		Integer group = model.getGroup();
		boolean root = model.isRoot();

		String rolesSearchString = this.decodeRolesString(roles);

		// Fields
		SearchDocumentBuilderUtility.addText(this.format, ROLES_FIELD, rolesSearchString, builder);
		SearchDocumentBuilderUtility.addAtom(this.format, GROUP_FIELD, group, builder);
		SearchDocumentBuilderUtility.addBoolean(this.format, ROOT_FIELD, root, builder);
	}

	private String decodeRolesString(Long roles) {
		String rolesString = null;

		if (roles != null && this.rolesDecoder != null) {
			Set<String> rolesSet = this.rolesDecoder.decode(roles);

			if (rolesSet.isEmpty() == false) {
				Joiner joiner = Joiner.on(ROLES_SPLITTER).skipNulls();
				rolesString = joiner.join(rolesSet);
			}
		}

		return rolesString;
	}

	@Override
	public String toString() {
		return "LoginDocumentBuilderStep [format=" + this.format + ", inclusionStep=" + this.inclusionStep + "]";
	}

}
