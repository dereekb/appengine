package com.dereekb.gae.model.extension.search.document.builder;

import com.dereekb.gae.server.search.query.SearchServiceQueryExpression;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameter;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameters;

/**
 * {@link SearchServiceQueryExpression} and {@link EncodedQueryParameter}.
 *
 * @author dereekb
 *
 */
public interface ModelSearch
        extends SearchServiceQueryExpression, EncodedQueryParameters {}
