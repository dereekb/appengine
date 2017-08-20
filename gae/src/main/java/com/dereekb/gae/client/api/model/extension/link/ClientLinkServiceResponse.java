package com.dereekb.gae.client.api.model.extension.link;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.client.api.model.extension.link.exception.ClientLinkSystemChangeErrorSet;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeResponseData;

/**
 * ClientLinkService response that contains the set of successful changes.
 * 
 * @author dereekb
 * 
 * @see ClientLinkService
 * @see ApiLinkChangeResponseData
 */
public interface ClientLinkServiceResponse {

	/**
	 * Returns a list of successful request identifiers.
	 *
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getSuccessful();

	/**
	 * Returns a list of failed request identifiers.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getFailed();
	
	/**
	 * Returns the error set, if available.
	 * 
	 * @return {@link List}. May be {@code null} if no errors available.
	 */
	public ClientLinkSystemChangeErrorSet getErrors();
	
	/**
	 * Returns a list containing all missing primary keys.
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelKey> getMissing();
	
}
