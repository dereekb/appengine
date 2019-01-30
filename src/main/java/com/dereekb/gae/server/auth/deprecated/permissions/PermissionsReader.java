package com.dereekb.gae.server.auth.deprecated.permissions;

import com.dereekb.gae.server.auth.deprecated.permissions.evaluator.PermissionsEvent;

/**
 * Reads permissions from a
 * @author dereekb
 *
 */
@Deprecated
public final class PermissionsReader {

	public static boolean hasPermission(String permissionString,
                                        PermissionsEvent event) {
	    // TODO Auto-generated method stub
	    return false;
    }

	/*
	private final List<Role> roles;

	public PermissionsReader(List<Role> roles) {
		this.roles = roles;
	}

	public boolean hasPermission(String permission) {
		boolean hasPermission = PermissionsReader.hasPermission(permission, this.roles);
		return hasPermission;
	}

	public static PermissionsReader reader(LoginAuthentication authentication) {
		Collection<Role> roles = authentication.get.getRoles();
		List<Role> rolesList = new ArrayList<Role>(roles);
		PermissionsReader reader = new PermissionsReader(rolesList);
		return reader;
	}

	public static PermissionsReader reader(PermissionAuthority authority) {
		Permissions permissions = authority.getPermissions();
		PermissionsReader reader = PermissionsReader.reader(permissions);
		return reader;
	}

	public static PermissionsReader reader(Permissions permissions) {
		List<Role> roles = permissions.getRoles();
		PermissionsReader reader = new PermissionsReader(roles);
		return reader;
	}

	public static boolean hasPermission(String permission, PermissionsEvent event) {
		LoginAuthentication authentication = event.getAuthentication();
		boolean hasPermission = PermissionsReader.hasPermission(permission, authentication);
		return hasPermission;
	}

	public static boolean hasPermission(String permission, LoginAuthentication authentication) {
		Collection<Role> roles = authentication.getRoles();
		List<Role> rolesList = new ArrayList<Role>(roles);
		boolean hasPermission = PermissionsReader.hasPermission(permission, rolesList);
		return hasPermission;
	}

	public static boolean hasPermission(String permission, PermissionAuthority authority) {
		Permissions permissions = authority.getPermissions();
		boolean hasPermission = PermissionsReader.hasPermission(permission, permissions);
		return hasPermission;
	}

	public static boolean hasPermission(String permission, Permissions permissions) {
		List<Role> roles = permissions.getRoles();
		boolean hasPermission = PermissionsReader.hasPermission(permission, roles);
		return hasPermission;
	}

	private static boolean hasPermission(String permission, List<Role> roles) {
		boolean hasPermission = false;

		Collections.sort(roles);
		Collections.reverse(roles);

		for (Role role : roles) {
			PermissionsSet permissionsSet = role.getPermissionsSet();
			hasPermission = permissionsSet.containsPermission(permission);

			if (hasPermission) {
				break;
			}
		}

		return hasPermission;
	}
	*/
}
