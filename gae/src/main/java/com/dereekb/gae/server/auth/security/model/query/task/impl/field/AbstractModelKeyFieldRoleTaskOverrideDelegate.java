package com.dereekb.gae.server.auth.security.model.query.task.impl.field;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContextGetter;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Abstract {@link AbstractModelKeyFieldTaskOverrideDelegate} extension that
 * uses a {@link AnonymousModelRoleSetContextService} to find stuff.
 *
 * @author dereekb
 *
 * @param <D>
 *            details type
 * @param <Q>
 *            query type
 */
public abstract class AbstractModelKeyFieldRoleTaskOverrideDelegate<D extends LoginTokenUserDetails<?>, Q> extends AbstractModelKeyFieldTaskOverrideDelegate<D, Q> {

	public static final String MISSING_ROLES_CODE = "MISSING_ROLES";

	protected static final ModelRole DEFAULT_QUERY_ROLE = CrudModelRole.SEARCH;

	private String type;
	private AnonymousModelRoleSetContextService roleService;

	public AbstractModelKeyFieldRoleTaskOverrideDelegate(String field,
	        String type,
	        AnonymousModelRoleSetContextService roleService) {
		super(field);
		this.setType(type);
		this.setRoleService(roleService);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		if (type == null) {
			throw new IllegalArgumentException("type cannot be null.");
		}

		this.type = type;
	}

	public AnonymousModelRoleSetContextService getRoleService() {
		return this.roleService;
	}

	public void setRoleService(AnonymousModelRoleSetContextService roleService) {
		if (roleService == null) {
			throw new IllegalArgumentException("roleService cannot be null.");
		}

		this.roleService = roleService;
	}

	// MARK: AnonymousModelRoleSetContextService
	@Override
	protected void assertHasAccessToFieldValue(ModelKeyQueryFieldParameter field,
	                                           Q input,
	                                           D details) {
		ModelKey key = field.getValue();
		AnonymousModelRoleSetContextGetter getter = this.roleService.getterForType(this.type);
		AnonymousModelRoleSetContext context = getter.getAnonymous(key);
		this.assertHasAccessRoles(context, input, details);
	}

	protected void assertHasAccessRoles(AnonymousModelRoleSetContext context,
	                                    Q input,
	                                    D details) {
		if (!this.checkHasAccessRoles(context, input, details)) {
			throw this.makeCannotAccessException(context);
		}
	}

	protected boolean checkHasAccessRoles(AnonymousModelRoleSetContext context,
	                                      Q input,
	                                      D details) {
		return this.checkHasAccessRoles(context.getRoleSet(), input, details);
	}

	protected boolean checkHasAccessRoles(ModelRoleSet roleSet,
	                                      Q input,
	                                      D details) {
		return roleSet.hasRole(DEFAULT_QUERY_ROLE);
	}

	protected RuntimeException makeCannotAccessException(AnonymousModelRoleSetContext context) {
		ModelKey key = context.getModelKey();
		return new InvalidAttributeException(this.getField(), key.toString(),
		        "You lack the roles to access this model.", MISSING_ROLES_CODE);
	}

}
