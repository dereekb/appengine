package com.dereekb.gae.model.extension.links.deprecated.service;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.links.deprecated.exception.ForbiddenLinkChangeException;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksAction;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Defines a service that handles relationships, or 'links' between types of objects.
 *
 * @author dereekb
 */
public interface LinksService<T extends UniqueModel> {

	/**
	 * Links the targeted objects to the specified objects.
	 *
	 * @param targets
	 *            The target objects being edited.
	 * @param linkKeys
	 *            Keys of other objects we want to link with the target.
	 * @param type
	 *            Type/Link key.
	 * @return List of elements edited.
	 */
	public List<T> linksChange(Collection<T> targets,
	                           Collection<ModelKey> linkKeys,
	                           LinksAction action,
	                           String type) throws ForbiddenLinkChangeException;

	/**
	 * Links the targeted objects to the specified objects.
	 *
	 * @param keys
	 *            Key of the target objects being edited.
	 * @param linkKeys
	 *            Keys of other objects we want to link with the target.
	 * @param type
	 *            Type/Link key.
	 * @return List of keys for the objects edited.ƒ
	 */
	public List<ModelKey> linksChangeWithIds(Collection<ModelKey> targetKeys,
	                                         Collection<ModelKey> linkKeys,
	                                  LinksAction action,
	                                         String type)
 throws ForbiddenLinkChangeException;

	public List<T> linksChangeWithModelRequest(LinksServiceModelRequest<T> request)
 throws ForbiddenLinkChangeException;

	public List<T> linksChangeWithModelRequests(Iterable<LinksServiceModelRequest<T>> requests)
	        throws ForbiddenLinkChangeException;

	// TODO: Move to another service type that supports automatic key request
	// loading.
	public List<ModelKey> linksChangeWithRequest(LinksServiceKeyRequest request)
 throws ForbiddenLinkChangeException;

	public List<ModelKey> linksChangeWithRequests(Iterable<LinksServiceKeyRequest> requests)
	        throws ForbiddenLinkChangeException;

}
