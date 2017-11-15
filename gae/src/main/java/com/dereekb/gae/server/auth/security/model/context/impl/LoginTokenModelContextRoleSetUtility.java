package com.dereekb.gae.server.auth.security.model.context.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.IndexCodedLoginTokenModelContextRole;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextCrudRole;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRole;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextRoleSetEncoderDecoder;
import com.dereekb.gae.server.auth.security.model.context.encoded.impl.LoginTokenModelContextRoleSetEncoderDecoderImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.misc.keyed.utility.KeyedUtility;

public class LoginTokenModelContextRoleSetUtility {

	public static LoginTokenModelContextRoleSetEncoderDecoder makeCrudDencoder() {
		IndexCodedLoginTokenModelContextRole[] roles = LoginTokenModelContextCrudRole.values();
		return makeDencoder(roles);
	}

	public static LoginTokenModelContextRoleSetEncoderDecoder makeDencoder(IndexCodedLoginTokenModelContextRole[]... roles) {
		List<IndexCodedLoginTokenModelContextRole> rolesList = ListUtility.flatten(roles);
		Map<Integer, ? extends LoginTokenModelContextRole> rolesMap = makeIndexedRolesMap(rolesList);
		Map<Integer, LoginTokenModelContextRole> map = new HashMap<Integer, LoginTokenModelContextRole>(rolesMap);

		LoginTokenModelContextRoleSetEncoderDecoderImpl dencoder = new LoginTokenModelContextRoleSetEncoderDecoderImpl(
		        map);
		return dencoder;
	}

	public static Map<Integer, ? extends LoginTokenModelContextRole> makeIndexedRolesMap(Iterable<IndexCodedLoginTokenModelContextRole> roles) {
		return KeyedUtility.makeCodedMap(roles);
	}

	public static Set<String> readRoles(Iterable<? extends LoginTokenModelContextRole> contextRoles) {
		Set<String> roles = new HashSet<String>();

		for (LoginTokenModelContextRole contextRole : contextRoles) {
			roles.add(contextRole.getRole());
		}

		return roles;
	}
	
}
