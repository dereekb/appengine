package com.thevisitcompany.gae.test.depr.server.auth.deprecated.permissions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.security.core.Authentication;

import com.thevisitcompany.gae.deprecated.authentication.login.security.LoginAuthentication;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.evaluator.NoPermissionHandlerFoundException;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.evaluator.PermissionEventHandlerFunction;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.evaluator.PermissionsEvaluator;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.evaluator.PermissionsEvent;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.evaluator.PermissionsHandler;

@RunWith(JUnit4.class)
public class PermissionEvaluatorTests {
	
	public static class TestingPermissionsHandler implements PermissionsHandler {
		
		private final String handlerName;
		
		public TestingPermissionsHandler(String name) {
			this.handlerName = name;
		}

		@PermissionEventHandlerFunction("can")
		public boolean canDoSomething(PermissionsEvent event) {
			return true;
		}

		@PermissionEventHandlerFunction("cant")
		public boolean cantDoSomething(PermissionsEvent event) {
			return false;
		}
		
		@PermissionEventHandlerFunction({"one", "two", "three"})
		public boolean handlesManySomethings(PermissionsEvent event) {
			return true;
		}

		@PermissionEventHandlerFunction("params")
		public boolean maybeCanDoSomething(PermissionsEvent event) {
			return event.hasParameters();
		}

		@Override
		public String getResponseKey() {
			return this.handlerName;
		}

		@Override
		public boolean hasPermission(PermissionsEvent event) {
			return false;
		}

		@Override
		public String getPermissionStringPrefix() {
			return "test";
		}
	}
	
	public static PermissionsEvaluator evaluator;

	@BeforeClass
	public static void setUp() {
		List<PermissionsHandler> handlers = new ArrayList<PermissionsHandler>();
		handlers.add(new TestingPermissionsHandler("a"));
		handlers.add(new TestingPermissionsHandler("b"));
		handlers.add(new TestingPermissionsHandler("c"));
		
		PermissionEvaluatorTests.evaluator = new PermissionsEvaluator(handlers);
	}

	@AfterClass
	public static void tearDown() {
		PermissionEvaluatorTests.evaluator = null;
	}
	
	@Test
	public void testPermissionEvents() {

		Login login = new Login();
		LoginAuthentication authentication = new LoginAuthentication(login, "details");
		Object object = "object";
		
		String permissionRequest = "handler.function.parameter.a.b.c";
		
		PermissionsEvent event = new PermissionsEvent(authentication, object, permissionRequest);
		assertNotNull(event.getAuthentication());
		assertNotNull(event.getObject());
		assertNotNull(event.getLogin());
		assertNotNull(event.getRequest());

		assertTrue(event.getRequestHandlerName().equals("handler"));
		assertTrue(event.getRequestFunctionName().equals("function"));
		assertTrue(event.getRequestParameters().equals("parameter.a.b.c"));
	}
	
	@Test
	public void testFunctions() {
		
		Login login = new Login();
		Authentication authentication = new LoginAuthentication(login, "details");
		Object object = "object";

		assertTrue(evaluator.hasPermission(authentication, object, "a.can"));
		assertFalse(evaluator.hasPermission(authentication, object, "a.cant"));

		assertTrue(evaluator.hasPermission(authentication, object, "b.can"));
		assertFalse(evaluator.hasPermission(authentication, object, "b.cant"));

		assertTrue(evaluator.hasPermission(authentication, object, "c.can"));
		assertFalse(evaluator.hasPermission(authentication, object, "c.cant"));

		assertTrue(evaluator.hasPermission(authentication, object, "a.one"));
		assertTrue(evaluator.hasPermission(authentication, object, "a.two"));
		assertTrue(evaluator.hasPermission(authentication, object, "a.three"));
		
		try {
			evaluator.hasPermission(authentication, object, "other");
			fail();
		} catch (NoPermissionHandlerFoundException e) { }
		
		assertTrue(evaluator.hasPermission(authentication, object, "a.params.true"));
		assertFalse(evaluator.hasPermission(authentication, object, "a.params"));
		
		assertFalse(evaluator.hasPermission(authentication, object, "a.someotherfunction"));
	}
	
	
}
