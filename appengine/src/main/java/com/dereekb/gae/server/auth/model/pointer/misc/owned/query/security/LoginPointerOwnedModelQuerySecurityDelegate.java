package com.dereekb.gae.server.auth.model.pointer.misc.owned.query.security;

import com.dereekb.gae.server.auth.model.pointer.link.LoginPointerLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.model.pointer.misc.owned.query.MutableLoginPointerOwnedQuery;
import com.dereekb.gae.server.auth.security.model.query.task.impl.AbstractSecurityModelQueryTaskOverrideDelegate;
import com.dereekb.gae.server.auth.security.model.query.task.impl.field.AbstractModelKeyFieldRoleTaskOverrideDelegate;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link AbstractSecurityModelQueryTaskOverrideDelegate} for an
 * {@link MutableLoginPointerOwnedQuery}.
 *
 * @author dereekb
 *
 */
public class LoginPointerOwnedModelQuerySecurityDelegate extends AbstractModelKeyFieldRoleTaskOverrideDelegate<LoginTokenUserDetails<?>, MutableLoginPointerOwnedQuery> {

	private static final String DEFAULT_FIELD_NAME = "loginPointer";
	private static final String DEFAULT_TYPE = LoginPointerLinkSystemBuilderEntry.LINK_MODEL_TYPE;

	public LoginPointerOwnedModelQuerySecurityDelegate(AnonymousModelRoleSetContextService roleService) {
		super(DEFAULT_FIELD_NAME, DEFAULT_TYPE, roleService);
	}

	public LoginPointerOwnedModelQuerySecurityDelegate(String field,
	        String type,
	        AnonymousModelRoleSetContextService roleService) {
		super(field, type, roleService);
	}

	// MARK: LoginPointerOwnedModelQuerySecurityDelegate
	@Override
	protected ModelKeyQueryFieldParameter getFieldFromQuery(MutableLoginPointerOwnedQuery input) {
		return input.getLoginPointer();
	}

	@Override
	protected void setDefaultFieldValue(MutableLoginPointerOwnedQuery query,
	                                    LoginTokenUserDetails<?> details)
	        throws InvalidAttributeException {
		query.setLoginPointer(details.getLoginPointerKey());
	}

	@Override
	public String toString() {
		return "LoginPointerOwnedModelQuerySecurityDelegate [getType()=" + this.getType() + ", getRoleService()="
		        + this.getRoleService() + ", getField()=" + this.getField() + "]";
	}

}
