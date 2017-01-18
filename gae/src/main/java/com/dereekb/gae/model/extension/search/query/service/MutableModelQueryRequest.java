package com.dereekb.gae.model.extension.search.query.service;

import com.dereekb.gae.server.datastore.objectify.query.MutableObjectifyQueryRequestOptions;

/**
 * {@link ModelQueryRequest} extension that is mutable.
 * 
 * @author dereekb
 *
 */
public interface MutableModelQueryRequest
        extends ModelQueryRequest, MutableObjectifyQueryRequestOptions {

}
