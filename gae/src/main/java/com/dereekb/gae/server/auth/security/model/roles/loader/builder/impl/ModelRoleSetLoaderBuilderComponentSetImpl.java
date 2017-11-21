package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetUtility;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilderComponentSet;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoadingSet;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapWithList;

/**
 * {@link ModelRoleSetLoaderBuilderComponentSet} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelRoleSetLoaderBuilderComponentSetImpl<T>
        implements ModelRoleSetLoaderBuilderComponentSet<T> {

	private List<ModelRoleSetLoaderBuilderComponent<T>> components;
	private transient CaseInsensitiveMapWithList<ModelRoleSetLoaderBuilderComponent<T>> map;
	private transient ModelRoleSetLoadingSet roleSet;

	public ModelRoleSetLoaderBuilderComponentSetImpl() {
		this.components = new ArrayList<ModelRoleSetLoaderBuilderComponent<T>>();
	};

	public ModelRoleSetLoaderBuilderComponentSetImpl(List<ModelRoleSetLoaderBuilderComponent<T>> components) {
		this.setComponents(components);
	};

	/**
	 * Copy constructor.
	 */
	protected ModelRoleSetLoaderBuilderComponentSetImpl(List<ModelRoleSetLoaderBuilderComponent<T>> components,
	        CaseInsensitiveMapWithList<ModelRoleSetLoaderBuilderComponent<T>> map,
	        ModelRoleSetLoadingSet roleSet) {
		super();
		this.setComponents(components);
		this.setMap(map);
		this.setRoleSet(roleSet);
	}

	public List<ModelRoleSetLoaderBuilderComponent<T>> getComponents() {
		return this.components;
	}

	public void setComponents(List<ModelRoleSetLoaderBuilderComponent<T>> components) {
		if (components == null) {
			throw new IllegalArgumentException("components cannot be null.");
		}

		this.components = new ArrayList<ModelRoleSetLoaderBuilderComponent<T>>(components);
		this.map = null;
		this.roleSet = null;
	}

	public CaseInsensitiveMapWithList<ModelRoleSetLoaderBuilderComponent<T>> getMap() {
		this.tryInitMap();
		return this.map;
	}

	private void setMap(CaseInsensitiveMapWithList<ModelRoleSetLoaderBuilderComponent<T>> map) {
		if (map == null) {
			throw new IllegalArgumentException("map cannot be null.");
		}

		this.map = new CaseInsensitiveMapWithList<ModelRoleSetLoaderBuilderComponent<T>>(map);
	}

	public ModelRoleSetLoadingSet getRoleSet() {
		this.tryInitMap();
		return this.roleSet;
	}

	public void setRoleSet(ModelRoleSetLoadingSet roleSet) {
		if (roleSet == null) {
			throw new IllegalArgumentException("roleSet cannot be null.");
		}

		this.roleSet = new ModelRoleSetLoadingSetImpl(roleSet);
	}

	// MARK: ModelRoleSetLoaderBuilderComponentSet
	@Override
	public ModelRoleSetLoaderBuilderComponentSetImpl<T> copySet() {
		return new ModelRoleSetLoaderBuilderComponentSetImpl<T>(this.getComponents(), this.getMap(), this.getRoleSet());
	}

	@Override
	public void add(ModelRoleSetLoaderBuilderComponent<T> component) {
		this.components.add(component);

		if (this.map != null) {
			this.addToMap(component);
		}
	}

	@Override
	public Collection<ModelRole> getAllRoleKeys() {
		return this.getRoleSet().getRoles();
	}

	@Override
	public Set<String> getAllRoleStringKeys() {
		return this.getMap().keySet();
	}

	@Override
	public List<ModelRoleSetLoaderBuilderComponent<T>> getAllComponents() {
		return ListUtility.safeCopy(this.components);
	}

	@Override
	public List<ModelRoleSetLoaderBuilderComponent<T>> getComponentsForRole(ModelRole role) {
		return this.getComponentsForRole(role.getRole());
	}

	@Override
	public List<ModelRoleSetLoaderBuilderComponent<T>> getComponentsForRole(String role) {
		return this.getMap().get(role);
	}

	// MARK: Internal
	protected void tryInitMap() {
		if (this.map != null) {
			this.initMap();
		}
	}

	protected void initMap() {
		this.roleSet = new ModelRoleSetLoadingSetImpl();
		this.map = new CaseInsensitiveMapWithList<ModelRoleSetLoaderBuilderComponent<T>>();

		for (ModelRoleSetLoaderBuilderComponent<T> component : this.components) {
			this.addToMap(component);
		}
	}

	protected void addToMap(ModelRoleSetLoaderBuilderComponent<T> component) {
		List<ModelRole> roles = component.getPotentialRoles();
		this.roleSet.addRoles(roles);
		Set<String> roleStrings = ModelRoleSetUtility.readRoles(roles);
		this.map.addAll(roleStrings, component);
	}

	@Override
	public String toString() {
		return "ModelRoleSetLoaderBuilderComponentSetImpl [components=" + this.components + ", map=" + this.map + "]";
	}

}
