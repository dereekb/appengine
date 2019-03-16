package com.dereekb.gae.server.app.model.hook.security;

import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.app.model.hook.link.AppHookLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoderDecoderEntry;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceEntryImpl;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetUtility;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link LoginTokenModelContextSetEncoderDecoderEntry} and
 * {@link LoginTokenModelContextServiceEntryImpl} for {@link AppHook}.
 *
 * @author dereekb
 *
 */
public class AppHookSecurityContextServiceEntry extends LoginTokenModelContextServiceEntryImpl<AppHook> {

	public AppHookSecurityContextServiceEntry(Getter<AppHook> getter, ModelRoleSetLoader<AppHook> rolesLoader) {
		super(AppHookLinkSystemBuilderEntry.LINK_MODEL_TYPE, ModelKeyType.NUMBER,
		        ModelRoleSetUtility.makeCrudDencoder(), getter, rolesLoader);
	}

}
