package com.dereekb.gae.server.auth.security.ownership.impl;

import com.dereekb.gae.server.auth.security.deprecated.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.deprecated.ownership.source.OwnershipRolesReader;

/**
 * {@link OwnershipRolesReader} implementation that returns an empty
 * {@link OwnershipRolesImpl}.
 *
 * @author dereekb
 *
 */
@Deprecated
public class NoOwnershipRolesReaderImpl<T>
        implements OwnershipRolesReader<T> {

	@Override
	public OwnershipRoles readRoles(T input) {
		return new OwnershipRolesImpl();
	}

}
