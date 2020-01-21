package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleImpl;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoadingSet;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.impl.MultiModelRoleGranterImpl;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;

/**
 * Abstract {@link ModelRoleSetLoaderBuilderComponent}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractModelRoleSetLoaderBuilderComponent<T>
        implements ModelRoleSetLoaderBuilderComponent<T> {

	private transient boolean initialized = false;
	private transient Map<String, ModelRoleGranter<T>> granters;
	private transient List<ModelRole> potentialRoles;

	protected AbstractModelRoleSetLoaderBuilderComponent() {}

	protected final void initGranters() {
		if (this.initialized) {
			return;
		}

		List<ModelRoleGranter<T>> loadedGranters = this.loadGranters();

		this.granters = new HashMap<String, ModelRoleGranter<T>>();
		this.potentialRoles = new ArrayList<ModelRole>();

		HashMapWithList<String, ModelRoleGranter<T>> grantersListMap = new HashMapWithList<String, ModelRoleGranter<T>>();

		for (ModelRoleGranter<T> granter : loadedGranters) {
			ModelRole role = granter.getGrantedRole();
			String roleString = role.getRole();

			this.potentialRoles.add(role);
			grantersListMap.add(roleString, granter);
		}

		for (Entry<String, List<ModelRoleGranter<T>>> granterEntry : grantersListMap.entrySet()) {
			String role = granterEntry.getKey();

			List<ModelRoleGranter<T>> granters = granterEntry.getValue();

			ModelRoleGranter<T> granter = null;

			if (granters.size() == 1) {
				granter = granters.get(0);
			} else {
				granter = new MultiModelRoleGranterImpl<T>(ModelRoleImpl.make(role), granters);
			}

			this.granters.put(role, granter);
		}

		this.initialized = true;
	}

	protected abstract List<ModelRoleGranter<T>> loadGranters();

	// MARK: ModelRoleSetLoaderBuilderComponent
	@Override
	public List<ModelRole> getPotentialRoles() {
		this.initGranters();
		return this.potentialRoles;
	}

	@Override
	public void loadRoles(T model,
	                      ModelRoleSetLoadingSet set) {
		this.initGranters();
		for (ModelRole role : this.getPotentialRoles()) {
			this.grantRole(model, role, set);
		}
	}

	@Override
	public boolean loadRole(T model,
	                        ModelRole role,
	                        ModelRoleSetLoadingSet set) {
		this.initGranters();
		return this.grantRole(model, role, set);
	}

	@Override
	public boolean hasRole(T model,
	                       ModelRole role) {
		this.initGranters();
		return this.checkGrantRole(model, role);
	}

	// MARK: Post-lazy initializers
	protected final boolean grantRole(T model,
	                                  ModelRole role,
	                                  ModelRoleSetLoadingSet set) {
		boolean grantedRole = this.checkGrantRole(model, role);
		this.grantRole(role, grantedRole, set);
		return grantedRole;
	}

	protected final boolean checkGrantRole(T model,
	                                       ModelRole role) {
		String roleString = role.getRole();
		ModelRoleGranter<T> granter = this.granters.get(roleString);

		if (granter == null) {
			return false;
		} else {
			return granter.hasRole(model);
		}
	}

	// MARK: Roles
	/**
	 * Modifies the set based on if it was granted or not.
	 */
	protected void grantRole(ModelRole role,
	                         boolean granted,
	                         ModelRoleSetLoadingSet set) {

		// Is additive only by default.
		if (granted) {
			set.addRole(role);
		}
	}

}
