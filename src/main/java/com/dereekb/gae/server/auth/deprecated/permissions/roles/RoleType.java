package com.dereekb.gae.server.auth.deprecated.permissions.roles;

import org.springframework.security.core.GrantedAuthority;

@Deprecated
public enum RoleType implements GrantedAuthority, Comparable<RoleType>{

	ADMIN (0, AdminRole.class),
    DEVELOPER (1, DeveloperRole.class),
    MODERATOR (2, ModeratorRole.class),
    USER (3, UserRole.class),
    NEW_USER (4, NewUserRole.class),
    PUBLIC (5, PublicRole.class);

	private final Class<? extends Role> roleClass;
    private final Integer bit;

    /**
     * Creates an authority with a specific bit representation. It's important that this doesn't
     * change as it will be used in the database. The enum ordinal is less reliable as the enum may be
     * reordered or have new roles inserted which would change the ordinal values.
     *
     * @param bit the permission bit which will represent this authority in the datastore.
     */
	RoleType(int bit, Class<? extends Role> roleClass) {
        this.bit = bit;
        this.roleClass = roleClass;
    }

	public static RoleType typeWithBit(int bit) {

		RoleType type = null;

		switch(bit) {
			case 0: type = ADMIN; break;
			case 1: type = DEVELOPER; break;
			case 2: type = MODERATOR; break;
			case 3: type = USER; break;
			case 4: type = NEW_USER; break;
			case 5: type = PUBLIC; break;
		}

		return type;
	}

    public Integer getBit() {
        return this.bit;
    }

    public Role roleInstance() {
		Role role = null;

		try {
			role = this.roleClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return role;
    }

	@Override
	public String getAuthority() {
        return this.toString();
	}

}
