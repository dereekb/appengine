package com.dereekb.gae.server.app.model.hook.security;

import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;

/**
 * {@link AbstractChildCrudModelRoleSetLoaderBuilderComponent} for
 * {@link AppHook}.
 *
 * @author dereekb
 *
 */
public class AppHookModelRoleBuilderComponent extends AbstractChildCrudModelRoleSetLoaderBuilderComponent<AppHook> {

	public AppHookModelRoleBuilderComponent(ParentModelRoleSetContextReader<AppHook> reader) {
		super(reader);
	}

}
