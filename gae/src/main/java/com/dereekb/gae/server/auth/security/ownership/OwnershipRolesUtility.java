package com.dereekb.gae.server.auth.security.ownership;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.auth.security.ownership.impl.OwnershipRolesImpl;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.google.common.base.Joiner;

public class OwnershipRolesUtility {

	private static final String OWNERSHIP_ROLES_SPLITTER = ",";

	// MARK: Encoding/Decoding
	public static String encodeRoles(OwnershipRoles roles) {
		List<String> rolesList = getRolesList(roles);

		Joiner joiner = Joiner.on(OWNERSHIP_ROLES_SPLITTER).skipNulls();
		return joiner.join(rolesList);
	}

	public static OwnershipRoles decodeRoles(String encodedOwnershipRoles) {
		OwnershipRoles ownershipRoles = null;

		if (encodedOwnershipRoles.isEmpty() == false) {
			String[] roles = encodedOwnershipRoles.split(OWNERSHIP_ROLES_SPLITTER);

			if (roles.length > 0) {
				String ownerId = roles[0];

				Set<String> additionalRoles = SetUtility.makeSet(roles);
				additionalRoles.remove(ownerId);

				ownershipRoles = new OwnershipRolesImpl(ownerId, additionalRoles);
			}
		}

		if (ownershipRoles == null) {
			ownershipRoles = new OwnershipRolesImpl();
		}

		return ownershipRoles;
	}

	// MARK: Utility
	public static List<String> getRolesList(OwnershipRoles roles) {
		List<String> rolesList = new ArrayList<String>();

		String ownerId = roles.getOwnerId();

		if (ownerId != null) {
			rolesList.add(ownerId);
			rolesList.addAll(roles.getAdditionalRoles());
		}

		return rolesList;
	}

}
