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

	private static boolean DEFAULT_REQUIRED = false;

	private ModelRole role = DEFAULT_QUERY_ROLE;
	private String type;
	private AnonymousModelRoleSetContextService roleService;

	private transient RoleAssertionChecker checker;

	public AbstractModelKeyFieldRoleTaskOverrideDelegate(String field) {
		this(field, DEFAULT_REQUIRED);
	}

	public AbstractModelKeyFieldRoleTaskOverrideDelegate(String field, boolean required) {
		super(field, required);
		this.clearRole();
	}

	public AbstractModelKeyFieldRoleTaskOverrideDelegate(String field,
	        String type,
	        AnonymousModelRoleSetContextService roleService) {
		this(field, DEFAULT_REQUIRED, type, roleService);
	}

	public AbstractModelKeyFieldRoleTaskOverrideDelegate(String field,
	        boolean required,
	        String type,
	        AnonymousModelRoleSetContextService roleService) {
		this(field, required, type, roleService, DEFAULT_QUERY_ROLE);
	}

	public AbstractModelKeyFieldRoleTaskOverrideDelegate(String field,
	        boolean required,
	        String type,
	        AnonymousModelRoleSetContextService roleService,
	        ModelRole role) {
		super(field, required);
		this.setType(type);
		this.setRoleService(roleService);
		this.setRole(role);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		if (type == null) {
			throw new IllegalArgumentException("Type cannot be null.");
		}

		this.type = type;
	}

	public AnonymousModelRoleSetContextService getRoleService() {
		return this.roleService;
	}

	public void setRoleService(AnonymousModelRoleSetContextService roleService) {
		if (roleService == null) {
			throw new IllegalArgumentException("RoleService cannot be null.");
		}

		this.roleService = roleService;
	}

	public ModelRole getRole() {
		return this.role;
	}

	public void setRole(ModelRole role) {
		if (role == null) {
			throw new IllegalArgumentException("Role cannot be null. Use clearRole() instead.");
		}

		this.role = role;
		this.checker = new RoleChecker();
	}

	public void clearRole() {
		this.role = null;
		this.checker = new SkipRoleChecker();
	}

	// MARK: AnonymousModelRoleSetContextService
	@Override
	protected void assertHasAccessToFieldValue(ModelKeyQueryFieldParameter field,
	                                           Q input,
	                                           D details) {
		this.checker.assertHasAccessToFieldValue(field, input, details);
	}

	// MARK: Role Checker
	protected abstract class RoleAssertionChecker {

		protected abstract void assertHasAccessToFieldValue(ModelKeyQueryFieldParameter field,
		                                                    Q input,
		                                                    D details);

	}

	protected class SkipRoleChecker extends RoleAssertionChecker {

		@Override
		protected void assertHasAccessToFieldValue(ModelKeyQueryFieldParameter field,
		                                           Q input,
		                                           D details) {
			// Do nothing.
		}

	}

	protected class RoleChecker extends RoleAssertionChecker {

		@Override
		protected void assertHasAccessToFieldValue(ModelKeyQueryFieldParameter field,
		                                           Q input,
		                                           D details) {
			ModelKey key = field.getValue();
			AnonymousModelRoleSetContextGetter getter = AbstractModelKeyFieldRoleTaskOverrideDelegate.this.roleService
			        .getterForType(AbstractModelKeyFieldRoleTaskOverrideDelegate.this.type);
			AnonymousModelRoleSetContext context = getter.getAnonymous(key);
			this.assertHasAccessRoles(context, input, details);
		}

		protected void assertHasAccessRoles(AnonymousModelRoleSetContext context,
		                                    Q input,
		                                    D details) {
			if (!this.checkHasAccessRoles(context, input, details)) {
				throw makeCannotAccessException(context);
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
			return roleSet.hasRole(AbstractModelKeyFieldRoleTaskOverrideDelegate.this.role);
		}

	}

	// MARK: Roles
	protected RuntimeException makeCannotAccessException(AnonymousModelRoleSetContext context) {
		ModelKey key = context.getModelKey();
		return new InvalidAttributeException(this.getField(), key.toString(),
		        "You lack the roles to access this model.", MISSING_ROLES_CODE);
	}

}
