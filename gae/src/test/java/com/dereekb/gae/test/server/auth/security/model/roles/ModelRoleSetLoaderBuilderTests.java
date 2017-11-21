package com.dereekb.gae.test.server.auth.security.model.roles;

import org.junit.Test;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilder;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.ModelRoleSetLoaderBuilderImpl;

public class ModelRoleSetLoaderBuilderTests {

	@Test
	public void testModelRoleSetLoaderBuilder() {
		ModelRoleSetLoaderBuilder<Login> builder = this.makeBuilder();

	}

	private ModelRoleSetLoaderBuilder<Login> makeBuilder() {
		ModelRoleSetLoaderBuilder<Login> builder = new ModelRoleSetLoaderBuilderImpl<Login>();

		ModelRoleSetLoaderBuilderComponent<Login> componentA = new ModelRoleSetLoaderBuilderComponent<Login>();
		builder.component(componentA);

		// TODO Auto-generated method stub
		return null;
	}

}
