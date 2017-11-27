package com.dereekb.gae.server.auth.model.pointer.security;

import com.dereekb.gae.server.auth.model.login.security.child.LoginParentModelRoleSetContextReaderDelegateImpl;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractParentChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.impl.SecurityContextAnonymousModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;
import com.dereekb.gae.server.auth.security.model.roles.parent.impl.ParentModelRoleSetContextReaderImpl;

/**
 * {@link AbstractParentChildCrudModelRoleSetLoaderBuilderComponent} for
 * {@link LoginPointer}.
 *
 * @author dereekb
 *
 */
public class LoginPointerModelRoleBuilderComponent extends AbstractChildCrudModelRoleSetLoaderBuilderComponent<LoginPointer> {

	public LoginPointerModelRoleBuilderComponent(SecurityContextAnonymousModelRoleSetContextService anonymousContextService) {
		super(makeReader(anonymousContextService));
	}

	private static ParentModelRoleSetContextReader<LoginPointer> makeReader(SecurityContextAnonymousModelRoleSetContextService contextService) {
		LoginParentModelRoleSetContextReaderDelegateImpl<LoginPointer> delegate = LoginParentModelRoleSetContextReaderDelegateImpl.make();
		return new ParentModelRoleSetContextReaderImpl<LoginPointer>(delegate, contextService);
	}

}
