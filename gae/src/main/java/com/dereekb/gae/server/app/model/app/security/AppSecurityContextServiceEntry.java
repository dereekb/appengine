package com.dereekb.gae.server.app.model.app.security;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.link.AppLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceEntryImpl;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetUtility;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link AppTokenModelContextSetEncoderDecoderEntry} and
 * {@link AppTokenModelContextServiceEntryImpl} for {@link App}.
 *
 * @author dereekb
 *
 */
public class AppSecurityContextServiceEntry extends LoginTokenModelContextServiceEntryImpl<App> {

	public AppSecurityContextServiceEntry(Getter<App> getter, ModelRoleSetLoader<App> rolesLoader) {
		super(AppLinkSystemBuilderEntry.LINK_MODEL_TYPE, ModelKeyType.NUMBER,
		        ModelRoleSetUtility.makeChildCrudDencoder(), getter, rolesLoader);
	}

}
