package com.dereekb.gae.test.applications.api.api.login.key;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginAuthenticationService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusServiceManager;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.server.auth.TestLoginTokenPair;
import com.dereekb.gae.web.api.auth.controller.key.KeyLoginController;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * Tests the login key controller and server manager.
 *
 * @author dereekb
 *
 */
public class KeyLoginApiControllerTest extends ApiApplicationTestContext {

	private JsonParser parser = new JsonParser();

	@Autowired
	private KeyLoginStatusServiceManager serviceManager;
	
	@Autowired
	private KeyLoginAuthenticationService authenticationService;
	
	@SuppressWarnings("unused")
	@Autowired
	private KeyLoginController keyLoginController;

	// MARK: KeyLogin tests
	@Test
	public void testKeyLoginServices() {
		
		TestLoginTokenPair pair = this.testLoginTokenContext.generateLogin("keyLogin");
		Login login = pair.getLogin();
		
		KeyLoginStatusService statusService = serviceManager.getService(login);
		
		//Test is not yet enabled.
		Assert.assertFalse(statusService.isEnabled());
		
		//Test it is not available.
		try {
			statusService.getKeyLoginPointerKey();
			Assert.fail();
		} catch (KeyLoginUnavailableException e) {}
		
		//Enable
		LoginPointer keyPointer = statusService.enable();
		
		Assert.assertNotNull(keyPointer);
		Assert.assertTrue(keyPointer.getLogin().equals(login.getObjectifyKey()));	
		Assert.assertTrue(statusService.isEnabled());
		
		//Test it is still enabled even with a new service.
		statusService = serviceManager.getService(login);
		Assert.assertTrue(statusService.isEnabled());
		
	}

	// MARK: Mock Tests

}
