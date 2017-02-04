package com.dereekb.gae.server.datastore.objectify.query.builder;

import com.dereekb.gae.utilities.query.builder.QueryFactory;

/**
 * {@link QueryFactory} that returns
 * {@link ConfigurableObjectifyQueryRequestConfigurer} results.
 * 
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public interface ObjectifyQueryFactory<Q extends ConfigurableObjectifyQueryRequestConfigurer>
        extends QueryFactory<Q> {

}
