package com.thevisitcompany.gae.test.deprecated.authentication;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thevisitcompany.gae.deprecated.authentication.login.AuthenticationReader;
import com.thevisitcompany.gae.deprecated.authentication.login.security.LoginAuthentication;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.Permissions;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.roles.AdminRole;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.roles.RoleType;
import com.thevisitcompany.gae.test.server.auth.deprecated.permissions.PermissionEvaluatorTests;

public class LoginAuthenticationTests {

	public static LoginAuthentication adminAuthentication;
	public static LoginAuthentication developerAuthentication;
	public static LoginAuthentication userAuthentication;

	@BeforeClass
	public static void setUp() {
		Permissions adminPermissions = new Permissions();
		adminPermissions.addRoleTypes(RoleType.ADMIN, RoleType.DEVELOPER, RoleType.USER);
		
		Login adminLogin = new Login("Admin", "admin@visit-service.com");
		adminLogin.setPermissions(adminPermissions);
		
		Permissions developerPermissions = new Permissions();
		developerPermissions.addRoleTypes(RoleType.DEVELOPER, RoleType.USER);

		Login developerLogin = new Login("Developer", "developer@visit-service.com");
		developerLogin.setPermissions(developerPermissions);

		adminAuthentication = new LoginAuthentication(adminLogin, "Object");
		developerAuthentication = new LoginAuthentication(developerLogin, "Object");
	}

	@AfterClass
	public static void tearDown() {
		PermissionEvaluatorTests.evaluator = null;	
	}
	
	@Test
	public void testReader() {
		
		AuthenticationReader reader = new AuthenticationReader(adminAuthentication);
		assertTrue(reader.hasAuthority("ROLE_ADMIN"));
		assertTrue(reader.hasAuthority(AdminRole.ROLE_NAME));
		assertTrue(reader.hasRole(new AdminRole()));
		assertTrue(reader.hasAuthority("ROLE_DEVELOPER"));
		assertTrue(reader.hasAuthority("ROLE_PUBLIC"));
		
	}
}
