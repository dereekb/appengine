package com.dereekb.gae.client.api.model.extension.search.query.response;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientKeyedInvalidAttributeException;
import com.dereekb.gae.model.extension.search.query.service.ModelQueryResponse;

/**
 * {@link ModelQueryResponse} extension that allows performing the next query,
 * if it exists.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientModelQueryResponse<T>
        extends ModelQueryResponse<T> {

	/**
	 * Whether or not there are more results to retrieve.
	 *
	 * @return {@code true} if there is a next query.
	 */
	public boolean hasNextQuery();

	/**
	 * Performs the "next" query, using the same arguments with the cursor
	 * returned from {{@link #getSearchCursor()}.
	 *
	 * Will fail if {{@link #hasNextQuery()} returns {@code false}.
	 *
	 * @return {@link ClientModelQueryResponse}. Never {@code null}.
	 * @throws UnsupportedOperationException
	 *             if the operation cannot be completed due to no security set,
	 *             or {@link #hasNextQuery()} returning false.
	 * @throws ClientKeyedInvalidAttributeException
	 *             thrown if one or more query attributes are invalid.
	 * @throws ClientIllegalArgumentException
	 *             thrown if the query is rejected.
	 * @throws ClientRequestFailureException
	 *             thrown if some other exception occurs.
	 */
	public ClientModelQueryResponse<T> performNextQuery()
	        throws UnsupportedOperationException,
	            ClientKeyedInvalidAttributeException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException;

}
