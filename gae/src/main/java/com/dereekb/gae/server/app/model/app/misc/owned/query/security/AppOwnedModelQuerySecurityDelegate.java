package com.dereekb.gae.server.app.model.app.misc.owned.query.security;

import com.dereekb.gae.server.app.model.app.link.AppLinkSystemBuilderEntry;
import com.dereekb.gae.server.app.model.app.misc.owned.query.MutableAppOwnedQuery;
import com.dereekb.gae.server.auth.security.model.query.task.impl.AbstractSecurityModelQueryTaskOverrideDelegate;
import com.dereekb.gae.server.auth.security.model.query.task.impl.field.AbstractModelKeyFieldRoleTaskOverrideDelegate;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * {@link AbstractSecurityModelQueryTaskOverrideDelegate} for an
 * {@link MutableAppOwnedQuery}.
 *
 * @author dereekb
 *
 */
public class AppOwnedModelQuerySecurityDelegate extends AbstractModelKeyFieldRoleTaskOverrideDelegate<LoginTokenUserDetails<?>, MutableAppOwnedQuery> {

	private static final String DEFAULT_FIELD_NAME = "app";
	private static final String DEFAULT_TYPE = AppLinkSystemBuilderEntry.LINK_MODEL_TYPE;

	public AppOwnedModelQuerySecurityDelegate(AnonymousModelRoleSetContextService roleService) {
		super(DEFAULT_FIELD_NAME, DEFAULT_TYPE, roleService);
	}

	public AppOwnedModelQuerySecurityDelegate(String field,
	        String type,
	        AnonymousModelRoleSetContextService roleService) {
		super(field, type, roleService);
	}

	// MARK: AppOwnedModelQuerySecurityDelegate
	@Override
	protected ModelKeyQueryFieldParameter getFieldFromQuery(MutableAppOwnedQuery input) {
		return input.getApp();
	}

	@Override
	public String toString() {
		return "AppOwnedModelQuerySecurityDelegate [getType()=" + this.getType() + ", getRoleService()="
		        + this.getRoleService() + ", getField()=" + this.getField() + "]";
	}

}
