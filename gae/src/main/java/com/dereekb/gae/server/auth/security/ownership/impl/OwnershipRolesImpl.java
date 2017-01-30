package com.dereekb.gae.server.auth.security.ownership.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;

/**
 * {@link OwnershipRoles} implementation.
 * 
 * @author dereekb
 *
 */
public final class OwnershipRolesImpl
        implements OwnershipRoles {

	private String ownerId;
	private Set<String> additionalRoles = Collections.emptySet();
	private Set<String> ownerRoles = Collections.emptySet();

	public OwnershipRolesImpl() {
		this(null, null);
	};

	public OwnershipRolesImpl(String ownerId) {
		this(ownerId, null);
	}

	public OwnershipRolesImpl(String ownerId, Set<String> additionalRoles) throws IllegalArgumentException {
		this.setOwnerId(ownerId);
		this.setAdditionalRoles(additionalRoles);
	}

	@Override
	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) throws IllegalArgumentException {
		if (ownerId != null && ownerId.isEmpty()) {
			throw new IllegalArgumentException("OwnerId cannot be empty.");
		}

		this.ownerId = ownerId;
		this.refreshRoles();
	}

	@Override
	public Set<String> getOwnerRoles() {
		return this.ownerRoles;
	}

	@Override
	public Set<String> getAdditionalRoles() {
		return this.additionalRoles;
	}

	public void setAdditionalRoles(Set<String> additionalRoles) {
		if (additionalRoles == null) {
			additionalRoles = Collections.emptySet();
		} else if (this.ownerId != null) {
			additionalRoles.remove(this.ownerId);
		}

		this.additionalRoles = additionalRoles;
		this.refreshRoles();
	}

	private void refreshRoles() {
		Set<String> ownerRoles = new HashSet<String>(this.additionalRoles);

		if (this.ownerId != null) {
			ownerRoles.add(this.ownerId);
		}

		this.ownerRoles = ownerRoles;
	}

	@Override
	public String toString() {
		return "OwnershipRolesImpl [ownerId=" + this.ownerId + ", additionalRoles=" + this.additionalRoles + "]";
	}

}
