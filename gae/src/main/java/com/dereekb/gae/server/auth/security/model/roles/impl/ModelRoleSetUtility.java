package com.dereekb.gae.server.auth.security.model.roles.impl;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.roles.IndexCodedModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.encoded.ModelRoleSetEncoderDecoder;
import com.dereekb.gae.server.auth.security.model.roles.encoded.impl.ModelRoleSetEncoderDecoderImpl;
import com.dereekb.gae.server.datastore.models.keys.exception.UninitializedModelKeyException;
import com.dereekb.gae.utilities.collections.map.KeyDelegate;
import com.dereekb.gae.utilities.collections.set.KeyedDelegatedSet;
import com.dereekb.gae.utilities.collections.set.impl.KeyedDelegatedSetImpl;

/**
 * {@link ModelRoleSet} utility.
 *
 * @author dereekb
 *
 */
public class ModelRoleSetUtility {

	private static transient ModelRoleSetEncoderDecoder CRUD_DENCODER;
	private static transient ModelRoleSetEncoderDecoder CHILD_CRUD_DENCODER;

	public static ModelRoleSetEncoderDecoder makeCrudDencoder() {
		if (CRUD_DENCODER == null) {
			CRUD_DENCODER =  makeDencoder(CrudModelRole.values());
		}

		return CRUD_DENCODER;
	}

	public static ModelRoleSetEncoderDecoder makeChildCrudDencoder() {
		if (CHILD_CRUD_DENCODER == null) {
			CHILD_CRUD_DENCODER = makeDencoder(CrudModelRole.values(), ChildCrudModelRole.values());
		}

		return CHILD_CRUD_DENCODER;
	}

	public static ModelRoleSetEncoderDecoder makeDencoder(IndexCodedModelRole[]... roles) {
		return ModelRoleSetEncoderDecoderImpl.makeBuilder(roles).build();
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
