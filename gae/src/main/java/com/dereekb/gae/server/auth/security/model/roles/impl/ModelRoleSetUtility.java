package com.dereekb.gae.server.auth.security.model.roles.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.roles.IndexCodedModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.encoded.ModelRoleSetEncoderDecoder;
import com.dereekb.gae.server.auth.security.model.roles.encoded.impl.ModelRoleSetEncoderDecoderImpl;
import com.dereekb.gae.server.datastore.models.keys.exception.UninitializedModelKeyException;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.map.KeyDelegate;
import com.dereekb.gae.utilities.collections.set.KeyedDelegatedSet;
import com.dereekb.gae.utilities.collections.set.impl.KeyedDelegatedSetImpl;
import com.dereekb.gae.utilities.misc.keyed.utility.KeyedUtility;

/**
 * {@link ModelRoleSet} utility.
 * 
 * @author dereekb
 *
 */
public class ModelRoleSetUtility {

	public static ModelRoleSetEncoderDecoder makeCrudDencoder() {
		IndexCodedModelRole[] roles = CrudModelRole.values();
		return makeDencoder(roles);
	}

	public static ModelRoleSetEncoderDecoder makeDencoder(IndexCodedModelRole[]... roles) {
		List<IndexCodedModelRole> rolesList = ListUtility.flatten(roles);
		Map<Integer, ? extends ModelRole> rolesMap = makeIndexedRolesMap(rolesList);
		Map<Integer, ModelRole> map = new HashMap<Integer, ModelRole>(rolesMap);

		ModelRoleSetEncoderDecoderImpl dencoder = new ModelRoleSetEncoderDecoderImpl(map);
		return dencoder;
	}

	public static Map<Integer, ? extends ModelRole> makeIndexedRolesMap(Iterable<IndexCodedModelRole> roles) {
		return KeyedUtility.makeCodedMap(roles);
	}

	public static Set<String> readRoles(Iterable<? extends ModelRole> contextRoles) {
		Set<String> roles = new HashSet<String>();

		for (ModelRole contextRole : contextRoles) {
			roles.add(contextRole.getRole());
		}

		return roles;
	}

	public static KeyedDelegatedSet<String, ModelRole> makeRoleSet() {
		return new KeyedDelegatedSetImpl<String, ModelRole>(ModelRoleKeyDelegate.SINGLETON);
	}

	public static class ModelRoleKeyDelegate
	        implements KeyDelegate<String, ModelRole> {

		private static final ModelRoleKeyDelegate SINGLETON = new ModelRoleKeyDelegate();

		private ModelRoleKeyDelegate() {}

		public ModelRoleKeyDelegate make() {
			return SINGLETON;
		}

		@Override
		public String keyForModel(ModelRole model) throws UninitializedModelKeyException {
			return model.getRole();
		}

	}

}
