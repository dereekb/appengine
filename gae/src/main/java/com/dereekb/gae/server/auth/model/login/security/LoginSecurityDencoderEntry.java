package com.dereekb.gae.server.auth.model.login.security;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.link.LoginLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.security.model.context.encoded.impl.LoginTokenModelContextSetEncoderDecoderEntryImpl;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetUtility;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link LoginTokenModelContextSetEncoderDecoderEntryImpl} for {@link Login}.
 *
 * @author dereekb
 *
 */
public class LoginSecurityDencoderEntry extends LoginTokenModelContextSetEncoderDecoderEntryImpl {

	public LoginSecurityDencoderEntry() {
		super(LoginLinkSystemBuilderEntry.LINK_MODEL_TYPE, ModelKeyType.NUMBER,
		        ModelRoleSetUtility.makeChildCrudDencoder());
	}

}
