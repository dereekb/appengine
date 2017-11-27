package com.dereekb.gae.server.auth.model.key.security;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.login.security.child.LoginParentModelRoleSetContextReaderDelegateImpl;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractParentChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.impl.SecurityContextAnonymousModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;
import com.dereekb.gae.server.auth.security.model.roles.parent.impl.ParentModelRoleSetContextReaderImpl;

/**
 * {@link AbstractParentChildCrudModelRoleSetLoaderBuilderComponent} for
 * {@link LoginKey}.
 *
 * @author dereekb
 *
 */
public class LoginKeyModelRoleBuilderComponent extends AbstractChildCrudModelRoleSetLoaderBuilderComponent<LoginKey> {

	public LoginKeyModelRoleBuilderComponent(SecurityContextAnonymousModelRoleSetContextService anonymousContextService) {
		super(makeReader(anonymousContextService));
	}

	private static ParentModelRoleSetContextReader<LoginKey> makeReader(SecurityContextAnonymousModelRoleSetContextService contextService) {
		LoginParentModelRoleSetContextReaderDelegateImpl<LoginKey> delegate = LoginParentModelRoleSetContextReaderDelegateImpl.make();
		return new ParentModelRoleSetContextReaderImpl<LoginKey>(delegate, contextService);
	}

}
