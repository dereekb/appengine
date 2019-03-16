package com.dereekb.gae.server.auth.model.login.security;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.link.LoginLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoderDecoderEntry;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceEntryImpl;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetUtility;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link LoginTokenModelContextSetEncoderDecoderEntry} and
 * {@link LoginTokenModelContextServiceEntryImpl} for {@link Login}.
 *
 * @author dereekb
 *
 */
public class LoginSecurityContextServiceEntry extends LoginTokenModelContextServiceEntryImpl<Login> {

	public LoginSecurityContextServiceEntry(Getter<Login> getter, ModelRoleSetLoader<Login> rolesLoader) {
		super(LoginLinkSystemBuilderEntry.LINK_MODEL_TYPE, ModelKeyType.NUMBER,
		        ModelRoleSetUtility.makeChildCrudDencoder(), getter, rolesLoader);
	}

}
