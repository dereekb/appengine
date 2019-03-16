package com.dereekb.gae.server.auth.model.key.security;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractParentChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;

/**
 * {@link AbstractParentChildCrudModelRoleSetLoaderBuilderComponent} for
 * {@link LoginKey}.
 *
 * @author dereekb
 *
 */
public class LoginKeyModelRoleBuilderComponent extends AbstractChildCrudModelRoleSetLoaderBuilderComponent<LoginKey> {

	public LoginKeyModelRoleBuilderComponent(ParentModelRoleSetContextReader<LoginKey> reader) {
		super(reader);
	}

}
