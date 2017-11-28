package com.dereekb.gae.server.auth.model.pointer.security;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.link.LoginPointerLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoderDecoderEntry;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceEntryImpl;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetUtility;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link LoginTokenModelContextSetEncoderDecoderEntry} and
 * {@link LoginTokenModelContextServiceEntryImpl} for {@link LoginPointer}.
 *
 * @author dereekb
 *
 */
public class LoginPointerSecurityContextServiceEntry extends LoginTokenModelContextServiceEntryImpl<LoginPointer> {

	public LoginPointerSecurityContextServiceEntry(Getter<LoginPointer> getter, ModelRoleSetLoader<LoginPointer> rolesLoader) {
		super(LoginPointerLinkSystemBuilderEntry.LINK_MODEL_TYPE, ModelKeyType.NAME,
		        ModelRoleSetUtility.makeCrudDencoder(), getter, rolesLoader);
	}

}
