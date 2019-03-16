package com.dereekb.gae.extras.gen.test.model.foo.security;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractParentChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.ownership.SecurityContextModelOwnershipChecker;

/**
 * {@link AbstractParentChildCrudModelRoleSetLoaderBuilderComponent} for
 * {@link Foo}.
 *
 * @author dereekb
 *
 */
public class FooModelRoleBuilderComponent extends AbstractParentChildCrudModelRoleSetLoaderBuilderComponent<Foo> {

	public FooModelRoleBuilderComponent() {
		this(new Checker());
	}

	public FooModelRoleBuilderComponent(SecurityContextModelOwnershipChecker<Foo> ownershipChecker) {
		super(ownershipChecker);
	}

	// MARK: SecurityContextModelOwnershipChecker
	private static final class Checker
	        implements SecurityContextModelOwnershipChecker<Foo> {

		@Override
		public boolean isOwnedInSecurityContext(Foo model) throws NoSecurityContextException {
			return true;	// Owned by all.
		}
	}

}
