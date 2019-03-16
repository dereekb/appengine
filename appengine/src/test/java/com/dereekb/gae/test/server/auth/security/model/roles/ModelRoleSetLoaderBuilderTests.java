package com.dereekb.gae.test.server.auth.security.model.roles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilder;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.ModelRoleSetLoaderBuilderImpl;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.impl.AutoModelRoleGranter;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * Tests {@link ModelRoleSetLoaderBuilder} and related components.
 *
 * @author dereekb
 *
 */
public class ModelRoleSetLoaderBuilderTests {

	@Test
	public void testModelRoleSetLoaderBuilder() {
		ModelRoleSetLoaderBuilder<Login> builder = this.makeBuilder();
		ModelRoleSetLoader<Login> loader = builder.buildLoader();

		Login login = new Login();
		ModelRoleSet set = loader.loadRolesForModel(login);

		assertFalse(set.hasRole(CrudModelRole.OWNED));
		assertTrue(set.hasRole(CrudModelRole.READ));
		assertTrue(set.hasRole(CrudModelRole.UPDATE));
		assertFalse(set.hasRole(CrudModelRole.DELETE));
	}

	// MARK: Setup
	private ModelRoleSetLoaderBuilder<Login> makeBuilder() {
		ModelRoleSetLoaderBuilder<Login> builder = new ModelRoleSetLoaderBuilderImpl<Login>();

		ModelRoleSetLoaderBuilderComponent<Login> componentA = new SimpleLoginBuilderAComponent();
		builder.component(componentA);

		ModelRoleSetLoaderBuilderComponent<Login> componentB = new SimpleLoginBuilderBComponent();
		builder.component(componentB);

		return builder;
	}

	private class SimpleLoginBuilderAComponent extends AbstractModelRoleSetLoaderBuilderComponent<Login> {

		@Override
		protected List<ModelRoleGranter<Login>> loadGranters() {
			List<ModelRoleGranter<Login>> granters = new ArrayList<ModelRoleGranter<Login>>();

			ListUtility.addElement(granters, AutoModelRoleGranter.make(CrudModelRole.OWNED, false));
			ListUtility.addElement(granters, AutoModelRoleGranter.make(CrudModelRole.READ, true));

			return granters;
		}

	}

	private class SimpleLoginBuilderBComponent extends AbstractModelRoleSetLoaderBuilderComponent<Login> {

		@Override
		protected List<ModelRoleGranter<Login>> loadGranters() {
			List<ModelRoleGranter<Login>> granters = new ArrayList<ModelRoleGranter<Login>>();

			ListUtility.addElement(granters, AutoModelRoleGranter.make(CrudModelRole.UPDATE, true));
			ListUtility.addElement(granters, AutoModelRoleGranter.make(CrudModelRole.DELETE, false));

			return granters;
		}

	}

}
