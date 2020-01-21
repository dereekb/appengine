package com.dereekb.gae.server.notification.model.token.security;

import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractParentChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;
import com.dereekb.gae.server.notification.model.token.NotificationSettings;

/**
 * {@link AbstractParentChildCrudModelRoleSetLoaderBuilderComponent} for
 * {@link NotificationSettings}.
 *
 * @author dereekb
 *
 */
public class NotificationSettingsModelRoleBuilderComponent extends AbstractChildCrudModelRoleSetLoaderBuilderComponent<NotificationSettings> {

	public NotificationSettingsModelRoleBuilderComponent(ParentModelRoleSetContextReader<NotificationSettings> reader) {
		super(reader);
	}

}
