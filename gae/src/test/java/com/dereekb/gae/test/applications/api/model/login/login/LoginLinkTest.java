package com.dereekb.gae.test.applications.api.model.login.login;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.model.extension.links.AbstractLinkServiceTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginLinkTest extends AbstractLinkServiceTest {

	@Autowired
	@Qualifier("loginType")
	private String loginLinkType;

	@Autowired
	@Qualifier("loginLoginPointerLinkName")
	private String loginLoginPointerLinkName;

	@Autowired
	@Qualifier("loginTestModelGenerator")
	private TestModelGenerator<Login> loginGenerator;

	@Autowired
	@Qualifier("loginPointerTestModelGenerator")
	private TestModelGenerator<LoginPointer> loginPointerGenerator;

	@Autowired
	@Qualifier("loginRegistry")
	private ObjectifyRegistry<Login> loginRegistry;

	@Autowired
	@Qualifier("loginPointerRegistry")
	private ObjectifyRegistry<LoginPointer> loginPointerRegistry;

	@Test
	public void testLinkingToLoginPointer() {
		Login login = this.loginGenerator.generate();
		LoginPointer loginPointer = this.loginPointerGenerator.generate();

		// Clear any generated links
		login.setPointers(null);
		this.loginRegistry.save(login, false);

		loginPointer.setLogin(null);
		this.loginPointerRegistry.save(loginPointer, false);

		// Start Test Linking
		this.linkModels(this.loginLinkType, login, this.loginLoginPointerLinkName, loginPointer);

		login = this.loginRegistry.get(login);
		loginPointer = this.loginPointerRegistry.get(loginPointer);

		Assert.assertNotNull(loginPointer.getLogin());
		Assert.assertTrue(loginPointer.getLogin().equals(login.getObjectifyKey()));
		Assert.assertTrue(login.getPointers().contains(loginPointer.getObjectifyKey()));

		// Test Unlinking
		this.unlinkModels(this.loginLinkType, login, this.loginLoginPointerLinkName, loginPointer);
		login = this.loginRegistry.get(login);
		loginPointer = this.loginPointerRegistry.get(loginPointer);

		Assert.assertNull(loginPointer.getLogin());
		Assert.assertFalse(login.getPointers().contains(loginPointer.getObjectifyKey()));
	}

}
