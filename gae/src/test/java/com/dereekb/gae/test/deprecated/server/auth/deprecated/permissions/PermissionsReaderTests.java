package com.thevisitcompany.gae.test.depr.server.auth.deprecated.permissions;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.thevisitcompany.gae.deprecated.authentication.login.security.LoginAuthentication;
import com.thevisitcompany.gae.deprecated.authentication.login.security.LoginAuthenticationService;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.PermissionsReader;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.evaluator.PermissionsEvent;

@Deprecated
@Ignore
@RunWith(JUnit4.class)
@SuppressWarnings("unused")
public class PermissionsReaderTests {

	/*
	@BeforeClass
	public static void setUpSecurity() {
		SpringContextGenerator.setAdministratorSecurityContext();
	}

	@Test
	public void testReadAuthentication() {

		Object object = null;
		String permissionRequest = "test";
		LoginAuthentication anonymousAuth = new LoginAuthentication(null, "Info");

		PermissionsEvent event = new PermissionsEvent(anonymousAuth, object, permissionRequest);

		assertTrue(PermissionsReader.hasPermission(permissionRequest, anonymousAuth));
		assertTrue(PermissionsReader.hasPermission(permissionRequest, event));

	}

	@Test
	public void testStaticReadPermissionTimes() {

		Login login = LoginAuthenticationService.getCurrentLogin();

		String permission = "test";
		boolean result = false;

		for (int i = 0; i < 10000000; i += 1) {
			result = PermissionsReader.hasPermission(permission, login);
		}

	}

	@Test
	public void testInstanceReadPermissionTimes() {

		Login login = LoginAuthenticationService.getCurrentLogin();

		String permission = "test";
		PermissionsReader permissionsReader = PermissionsReader.reader(login);
		boolean result = false;

		for (int i = 0; i < 10000000; i += 1) {
			result = permissionsReader.hasPermission(permission);
		}

	}
	*/

}
