package com.dereekb.gae.server.auth.model.key.security;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.key.link.LoginKeyLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoderDecoderEntry;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceEntryImpl;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetUtility;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link LoginTokenModelContextSetEncoderDecoderEntry} and
 * {@link LoginTokenModelContextServiceEntryImpl} for {@link LoginKey}.
 *
 * @author dereekb
 *
 */
public class LoginKeySecurityContextServiceEntry extends LoginTokenModelContextServiceEntryImpl<LoginKey> {

	public LoginKeySecurityContextServiceEntry(Getter<LoginKey> getter, ModelRoleSetLoader<LoginKey> rolesLoader) {
		super(LoginKeyLinkSystemBuilderEntry.LINK_MODEL_TYPE, ModelKeyType.NUMBER,
		        ModelRoleSetUtility.makeCrudDencoder(), getter, rolesLoader);
	}

}
