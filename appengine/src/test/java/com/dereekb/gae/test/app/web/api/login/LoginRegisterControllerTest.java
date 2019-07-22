package com.dereekb.gae.test.app.web.api.login;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext;
import com.dereekb.gae.web.api.auth.controller.anonymous.AnonymousLoginController;
import com.dereekb.gae.web.api.auth.controller.password.PasswordLoginController;
import com.dereekb.gae.web.api.auth.controller.register.LoginRegisterController;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Tests the register controller and register service.
 * <p>
 * Tests do NOT use the mock API.
 *
 * @author dereekb
 *
 */
public class LoginRegisterControllerTest extends AbstractAppTestingContext {

	private static final String LOGIN_REGISTER_URL = "/login/auth/register";

	private static final String TEST_USERNAME = "tUsername";
	private static final String TEST_PASSWORD = "tPassword";

	@Autowired(required = false)
	@Qualifier("anonymousLoginController")
	public AnonymousLoginController anonymousController;

	@Autowired
	@Qualifier("passwordLoginController")
	public PasswordLoginController passwordController;

	@Autowired
	@Qualifier("loginRegisterController")
	public LoginRegisterController registerController;

	@Autowired
	@Qualifier("loginRegisterService")
	public LoginRegisterService registerService;

	@Autowired
	@Qualifier("loginTokenService")
	public LoginTokenService<LoginToken> loginTokenService;

	@Autowired
	@Qualifier("loginPointerRegistry")
	public ObjectifyRegistry<LoginPointer> loginPointerRegistry;

	// MARK: LoginService Tests
	@Test
	public void testLoginServiceRegister() {
		LoginTokenPair pair = this.passwordController.create(TEST_USERNAME, TEST_PASSWORD);
		LoginPointer pointer = this.loginPointerRegistry.get(pair.getLoginPointerKey());

		try {
			Login login = this.registerService.register(pointer);
			assertNotNull(login);
		} catch (LoginExistsException | LoginRegistrationRejectedException e) {
			fail();
		}
	}

	@Test
	public void testLoginServiceRegisterTokens() throws LoginExistsException, LoginRegistrationRejectedException {
		LoginTokenPair primary = this.passwordController.create(TEST_USERNAME, TEST_PASSWORD);
		LoginPointer pointer = this.loginPointerRegistry.get(primary.getLoginPointerKey());
		Login login = this.registerService.register(pointer);

		String[] usernames = new String[] { "A", "B", "C" };
		Set<String> pointers = new HashSet<>();

		for (String username : usernames) {
			LoginTokenPair pair = this.passwordController.create(username, TEST_PASSWORD);
			pointers.add(pair.getLoginPointer());
		}

		this.registerService.registerPointersToLogin(login.getModelKey(), pointers);
	}

	// MARK: LoginRegisterController
	@Test
	public void testRegisterControllerRegisterLogins() throws LoginExistsException, LoginRegistrationRejectedException {
		LoginTokenPair primary = this.passwordController.create(TEST_USERNAME, TEST_PASSWORD);
		LoginPointer pointer = this.loginPointerRegistry.get(primary.getLoginPointerKey());
		Login login = this.registerService.register(pointer);
		pointer.setLogin(login.getObjectifyKey());

		String primaryToken = this.loginTokenService.encodeLoginToken(pointer, true);

		String[] usernames = new String[] { "A", "B", "C" };
		List<String> tokens = new ArrayList<>();
		tokens.add(primaryToken);

		for (String username : usernames) {
			LoginTokenPair pair = this.passwordController.create(username, TEST_PASSWORD);
			String token = pair.getToken();
			tokens.add(token);
		}

		this.registerController.registerTokensWithLogin(tokens);

		// Assert those login pointers reference the login...
	}

	// MARK: Mock Controllers
	@Test
	public void testRegister() throws Exception {

		// Create a password LoginPointer/Token
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);
		String token = testUtility.createPasswordLogin(TEST_USERNAME, TEST_PASSWORD);
		DecodedLoginToken<LoginToken> decodedPasswordToken = this.loginTokenService.decodeLoginToken(token);

		LoginToken passwordLoginToken = decodedPasswordToken.getLoginToken();
		assertFalse(passwordLoginToken.isRegistered());

		// Login Again
		testUtility.loginWithPassword(TEST_USERNAME, TEST_PASSWORD);

		// Register
		String fullUserToken = testUtility.register(token);

		DecodedLoginToken<LoginToken> decodedFullToken = this.loginTokenService.decodeLoginToken(fullUserToken);
		assertTrue(decodedFullToken.getLoginToken().isRegistered());
	}

	@Disabled
	@Test
	public void testAnonymousRegisteringFails() throws Exception {

		if (this.anonymousController == null) {
			return;
		}

		this.testLoginTokenContext.generateAnonymousLogin();

		MockHttpServletRequestBuilder registerRequestBuilder = this.serviceRequestBuilder.post(LOGIN_REGISTER_URL);
		MvcResult result = this.performHttpRequest(registerRequestBuilder).andReturn();

		assertTrue(result.getResponse().getStatus() == HttpServletResponse.SC_FORBIDDEN);
	}

}
