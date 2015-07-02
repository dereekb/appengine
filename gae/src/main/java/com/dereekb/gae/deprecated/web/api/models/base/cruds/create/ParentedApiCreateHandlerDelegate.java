package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.create;

import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;

public interface ParentedApiCreateHandlerDelegate<T extends KeyedModel<K>, K> {

	public List<T> createWithNames(K parent,
	                               List<String> names)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException;

	public List<T> createWithSources(K parent,
	                                 List<T> sources)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException;

}
