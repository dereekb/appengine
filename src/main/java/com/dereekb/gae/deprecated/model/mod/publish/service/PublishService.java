package com.thevisitcompany.gae.deprecated.model.mod.publish.service;

import java.util.Collection;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.PublishAction;

/**
 * Defines a service that handles relationships, or 'links' between types of objects.
 * 
 * @author dereekb
 */
public interface PublishService<T extends KeyedPublishableModel<K>, K> {

	/**
	 * Processes a publish change on an set of objects of type <T>.
	 * 
	 * @param input
	 * @param action
	 * @return Identifiers of the models that were changed successfully.
	 * @throws ForbiddenObjectChangesException
	 */
	public Collection<K> publishChange(Iterable<T> input,
	                                   PublishAction action) throws ForbiddenObjectChangesException;

	/**
	 * Processes a publish change on an set of identifiers.
	 * 
	 * @param identifiers
	 * @param action
	 * @return Identifiers of the models that were changed successfully.
	 * @throws ForbiddenObjectChangesException
	 * @throws UnavailableObjectsException
	 */
	public Collection<K> publishChangeWithIds(Iterable<K> input,
	                                          PublishAction action)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException;

}
