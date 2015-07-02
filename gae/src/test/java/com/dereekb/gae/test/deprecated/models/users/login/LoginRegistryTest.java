package com.thevisitcompany.gae.test.deprecated.models.users.login;

import org.junit.Ignore;

import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.deprecated.model.users.login.LoginRegistry;
import com.thevisitcompany.gae.deprecated.model.users.login.utility.LoginGenerator;
import com.thevisitcompany.gae.test.deprecated.models.support.registry.AbstractModelRegistryTest;

// TODO: Ignore for now, since LoginGenerator is incomplete. Complete Login Generator.
@Ignore
public class LoginRegistryTest extends AbstractModelRegistryTest<Login> {

	private static final LoginGenerator generator = new LoginGenerator();
	private static final LoginRegistry registry = new LoginRegistry();

	public LoginRegistryTest() {
		super(registry);
	}

	@Override
	protected Login generateModel() {
		return generator.generateModel(null);
	}

}
