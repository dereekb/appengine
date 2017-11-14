package com.dereekb.gae.server.auth.security.model.context;

import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.exception.UnavailableModelContextTypeException;

/**
 * Simple accessor for {@link LoginTokenModelContext} values.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContextSet {

	/**
	 * Returns the set map.
	 * 
	 * @return {@link Map}. Never {@code null}.
	 */
	public Map<String, LoginTokenTypedModelContextSet> getSetMap();

	/**
	 * Returns the set of all model types.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getModelTypes();

	/**
	 * Checks if the specified type exists or not.
	 * 
	 * @param modelType
	 *            {@link String}. Never {@code null}.
	 * @return {@code true} if the context is available.
	 */
	public boolean hasContextForType(String modelType);

	/**
	 * Returns the set.
	 * 
	 * @param modelType
	 *            {@link String}. Never {@code null}.
	 * @return {@link LoginTokenTypedModelContextSet}. Never {@code null}.
	 * @throws UnavailableModelContextTypeException
	 *             if no contexts for this type exist.
	 */
	public LoginTokenTypedModelContextSet getContextsForType(String modelType)
	        throws UnavailableModelContextTypeException;

}
