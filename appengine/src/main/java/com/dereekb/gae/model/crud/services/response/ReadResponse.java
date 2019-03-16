package com.dereekb.gae.model.crud.services.response;

import com.dereekb.gae.model.crud.services.components.ReadService;

/**
 * Response returned by a {@link ReadService}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see ReadRequest
 */
public interface ReadResponse<T>
        extends ModelServiceResponse<T>, SimpleReadResponse<T> {

}
