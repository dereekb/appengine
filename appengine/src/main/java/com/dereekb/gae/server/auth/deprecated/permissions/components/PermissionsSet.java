package com.dereekb.gae.server.auth.deprecated.permissions.components;

/**
 * A "set" of permissions that can be checked to see if a permission exists or not.
 * @author dereekb
 */
public interface PermissionsSet {

	/**
	 * Returns true if the set contains a given permission. 
	 * 
	 * @param permission Permission without wildcards.
	 * @return
	 */
	public boolean containsPermission(String permission);
	
	/**
	 * Returns true if the set satisfies a given permission.
	 * @param permission Permission that can contain wildcards.
	 * @return
	 */
	public boolean satisfiesPermission(String permission);
}
