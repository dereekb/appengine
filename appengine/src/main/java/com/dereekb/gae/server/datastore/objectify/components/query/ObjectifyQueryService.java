package com.dereekb.gae.server.datastore.objectify.components.query;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilderFactory;

/**
 * Used for performing Objectify queries.
 *
 * @author dereekb
 */
public interface ObjectifyQueryService<T extends ObjectifyModel<T>>
        extends ObjectifyIterationQueryService<T>, ObjectifyEntityQueryService<T>, ObjectifyExistenceQueryService<T>,
        ObjectifyQueryRequestBuilderFactory<T> {

}
