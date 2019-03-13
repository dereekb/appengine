package com.thevisitcompany.gae.deprecated.authentication.registration;

import java.util.ArrayList;
import java.util.Collection;

import com.thevisitcompany.gae.deprecated.authentication.registration.temporary.CreateAccountHandlerFunction;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.deprecated.model.users.login.LoginRegistry;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.Permissions;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.roles.RoleType;

/**
 * Handles registration of new users.
 * 
 * @author dereekb
 * 
 */
public class RegistrationHandler {

	public static LoginRegistry loginRegistry = LoginRegistry.getRegistry();

	private final Collection<RegistrationHandlerFunction> functions;

	public RegistrationHandler() {
		this.functions = new ArrayList<RegistrationHandlerFunction>();
		
		//TODO: Remove this and implement it as a Spring Bean.
		this.functions.add(new CreateAccountHandlerFunction());
	}

	public RegistrationHandler(Collection<RegistrationHandlerFunction> functions) {
		this.functions = functions;
	}

	private Login createLogin(Login source) {
		Login login = new Login(source);

		return login;
	}

	private void updateLoginWithFunctions(Login login) {
		for (RegistrationHandlerFunction function : this.functions) {
			function.modifyRegisteredLogin(login);
		}
	}

	public Login registerUser(Login login) {

		Login user = this.createLogin(login);

		Permissions permissions = new Permissions();
		permissions.addRoleTypes(RoleType.USER);

		user.setPermissions(permissions);
		user.setActive(true);

		loginRegistry.save(user, false);

		this.updateLoginWithFunctions(user);

		loginRegistry.save(user, true);

		return user;
	}

	public Login registerAdministrator(Login login) {

		Login administrator = this.createLogin(login);

		Permissions adminPermissions = new Permissions();
		adminPermissions.addRoleTypes(RoleType.ADMIN, RoleType.MODERATOR, RoleType.DEVELOPER, RoleType.USER);

		administrator.setPermissions(adminPermissions);
		administrator.setActive(true);

		loginRegistry.save(administrator, false);

		this.updateLoginWithFunctions(administrator);

		loginRegistry.save(administrator, true);

		return administrator;
	}

}
