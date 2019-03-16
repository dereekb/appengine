package com.dereekb.gae.server.auth.security.model.roles.loader.builder;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;

/**
 * Manages both a list of components, as well as the order of items in a map.
 * 
 * @author dereekb
 *
 */
public interface ModelRoleSetLoaderBuilderComponentSet<T> {

	/**
	 * Creates a copy of the set.
	 * 
	 * @return {@link ModelRoleSetLoaderBuilderComponentSet}. Never
	 *         {@code null}.
	 */
	public ModelRoleSetLoaderBuilderComponentSet<T> copySet();

	/**
	 * Adds the component to the set.
	 * 
	 * @param component
	 *            {@link ModelRoleSetLoaderBuilderComponent}. Never
	 *            {@code null}.
	 */
	public void add(ModelRoleSetLoaderBuilderComponent<T> component);

	/**
	 * Returns the collection of all potential role keys.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<ModelRole> getAllRoleKeys();

	/**
	 * Returns the set of all potential role string keys.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getAllRoleStringKeys();

	/**
	 * Returns the list of components for a specific role.
	 * 
	 * @param role
	 *            {@link ModelRole}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelRoleSetLoaderBuilderComponent<T>> getComponentsForRole(ModelRole role);

	/**
	 * Returns the list of components for a specific role string.
	 * 
	 * @param role
	 *            {@link String}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelRoleSetLoaderBuilderComponent<T>> getComponentsForRole(String role);

	/**
	 * Returns all components.
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelRoleSetLoaderBuilderComponent<T>> getAllComponents();

}
