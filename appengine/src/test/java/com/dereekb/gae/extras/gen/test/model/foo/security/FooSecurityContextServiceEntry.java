package com.dereekb.gae.extras.gen.test.model.foo.security;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.extras.gen.test.model.foo.link.FooLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoderDecoderEntry;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceEntryImpl;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetUtility;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link LoginTokenModelContextSetEncoderDecoderEntry} and
 * {@link LoginTokenModelContextServiceEntryImpl} for {@link Foo}.
 *
 * @author dereekb
 *
 */
public class FooSecurityContextServiceEntry extends LoginTokenModelContextServiceEntryImpl<Foo> {

	public FooSecurityContextServiceEntry(Getter<Foo> getter, ModelRoleSetLoader<Foo> rolesLoader) {
		super(FooLinkSystemBuilderEntry.LINK_MODEL_TYPE, ModelKeyType.NAME,
		        ModelRoleSetUtility.makeCrudDencoder(), getter, rolesLoader);
	}

}
