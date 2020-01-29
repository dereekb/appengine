package com.dereekb.gae.test.app.web.api.model.client;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.app.api.AbstractModelClientTests;

/**
 *
 * @author dereekb
 *
 */
public class LoginClientTests extends AbstractModelClientTests {

	@Autowired
	@Qualifier("loginRegistry")
	private ObjectifyRegistry<Login> loginRegistry;

	@Autowired
	@Qualifier("loginPointerRegistry")
	private ObjectifyRegistry<LoginPointer> loginPointerRegistry;

	private BasicTestUserSetupImpl testAdmin;
	private BasicTestUserSetupImpl testUserA;

	private BasicTestingInstance testAdminInstance;
	private BasicTestingInstance testUserAInstance;

	@BeforeEach
	public void initForTest() {
		this.testAdmin = new BasicTestUserSetupImpl("admin", true);
		this.testUserA = new BasicTestUserSetupImpl();

		this.testAdminInstance = new BasicTestingInstance(this.testAdmin);
		this.testUserAInstance = new BasicTestingInstance(this.testUserA);
	}

	// MARK: Tests
	@Test
	public void testDisablingALoginDisablesItsLoginPointers() {

		Login loginToDisable = this.testUserAInstance.testUser.getLogin();

		Login loginTemplate = new Login();
		loginTemplate.setModelKey(loginToDisable.getModelKey());
		loginTemplate.setDisabled(true);
		loginTemplate.setAuthReset(null);

		Login updatedLogin = this.testAdminInstance.login.update(loginTemplate);
		assertTrue(updatedLogin.getDisabled());
		waitUntilTaskQueueCompletes();

		LoginPointer loginPointer = this.loginPointerRegistry.get(this.testUserA.pair.getLoginPointer());
		Login readLogin = this.loginRegistry.get(loginToDisable);
		assertTrue(readLogin.isSynchronized());

		assertTrue(loginPointer.getDisabled());
	}

	@Test
	public void testUserCannotDisableTheirOwnAccount() {

		Login loginToDisable = this.testUserAInstance.testUser.getLogin();

		Login loginTemplate = new Login();
		loginTemplate.setModelKey(loginToDisable.getModelKey());
		loginTemplate.setDisabled(true);
		loginTemplate.setAuthReset(null);

		this.testUserAInstance.login.makeTestingSet().testUpdateFailsDueToKeyedAttributeFailure(loginTemplate, "disabled");
	}

	/*
	 * TODO: Test user cannot disable another user's account.
	 */

}
