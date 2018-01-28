package com.dereekb.gae.server.app.model.app.shared.security;

import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;
import com.dereekb.gae.server.app.model.app.shared.impl.AbstractAppRelatedModel;

/**
 * {@link AbstractChildCrudModelRoleSetLoaderBuilderComponent} for a
 * {@link AbstractAppRelatedModel}.
 * <p>
 * Grants roles based on the app's current granted permissions.
 *
 * @author dereekb
 *
 */
public class AppRelatedModelAppModelRoleBuilderComponent<T extends AbstractAppRelatedModel<T>> extends AbstractChildCrudModelRoleSetLoaderBuilderComponent<T> {

	public AppRelatedModelAppModelRoleBuilderComponent(ParentModelRoleSetContextReader<T> parentReader) {
		super(parentReader);
	}

}
