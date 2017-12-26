package com.dereekb.gae.server.auth.model.pointer.security;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractParentChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;

/**
 * {@link AbstractParentChildCrudModelRoleSetLoaderBuilderComponent} for
 * {@link LoginPointer}.
 *
 * @author dereekb
 *
 */
public class LoginPointerModelRoleBuilderComponent extends AbstractChildCrudModelRoleSetLoaderBuilderComponent<LoginPointer> {

	public LoginPointerModelRoleBuilderComponent(ParentModelRoleSetContextReader<LoginPointer> reader) {
		super(reader);
	}

}
