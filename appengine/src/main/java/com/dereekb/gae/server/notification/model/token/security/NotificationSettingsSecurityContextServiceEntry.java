package com.dereekb.gae.server.notification.model.token.security;

import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoderDecoderEntry;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceEntryImpl;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetUtility;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.notification.model.token.NotificationSettings;
import com.dereekb.gae.server.notification.model.token.link.NotificationSettingsLinkSystemBuilderEntry;

/**
 * {@link LoginTokenModelContextSetEncoderDecoderEntry} and
 * {@link LoginTokenModelContextServiceEntryImpl} for
 * {@link NotificationSettings}.
 *
 * @author dereekb
 *
 */
public class NotificationSettingsSecurityContextServiceEntry extends LoginTokenModelContextServiceEntryImpl<NotificationSettings> {

	public NotificationSettingsSecurityContextServiceEntry(Getter<NotificationSettings> getter,
	        ModelRoleSetLoader<NotificationSettings> rolesLoader) {
		super(NotificationSettingsLinkSystemBuilderEntry.LINK_MODEL_TYPE, ModelKeyType.NUMBER,
		        ModelRoleSetUtility.makeCrudDencoder(), getter, rolesLoader);
	}

}
