package com.dereekb.gae.server.auth.model.login.misc.owned.query.security;

import com.dereekb.gae.server.auth.model.login.link.LoginLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.model.login.misc.owned.query.MutableLoginOwnedQuery;
import com.dereekb.gae.server.auth.security.model.query.task.impl.AbstractSecurityModelQueryTaskOverrideDelegate;
import com.dereekb.gae.server.auth.security.model.query.task.impl.field.AbstractModelKeyFieldRoleTaskOverrideDelegate;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link AbstractSecurityModelQueryTaskOverrideDelegate} for an
 * {@link MutableLoginOwnedQuery}.
 *
 * @author dereekb
 *
 */
public class LoginOwnedModelQuerySecurityDelegate extends AbstractModelKeyFieldRoleTaskOverrideDelegate<LoginTokenUserDetails<?>, MutableLoginOwnedQuery> {

	private static final String DEFAULT_FIELD_NAME = "login";
	private static final String DEFAULT_TYPE = LoginLinkSystemBuilderEntry.LINK_MODEL_TYPE;

	public LoginOwnedModelQuerySecurityDelegate(AnonymousModelRoleSetContextService roleService) {
		super(DEFAULT_FIELD_NAME, DEFAULT_TYPE, roleService);
	}

	public LoginOwnedModelQuerySecurityDelegate(String field,
	        String type,
	        AnonymousModelRoleSetContextService roleService) {
		super(field, type, roleService);
	}

	// MARK: LoginOwnedModelQuerySecurityDelegate
	@Override
	protected ModelKeyQueryFieldParameter getFieldFromQuery(MutableLoginOwnedQuery input) {
		return input.getLogin();
	}

	@Override
	protected void setDefaultFieldValue(MutableLoginOwnedQuery query,
	                                    LoginTokenUserDetails<?> details)
	        throws InvalidAttributeException {
		query.setLogin(details.getLoginKey());
	}

	@Override
	public String toString() {
		return "LoginOwnedModelQuerySecurityDelegate [getType()=" + this.getType() + ", getRoleService()="
		        + this.getRoleService() + ", getField()=" + this.getField() + "]";
	}

}
