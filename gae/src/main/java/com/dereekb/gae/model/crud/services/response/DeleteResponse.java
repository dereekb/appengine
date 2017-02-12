package com.dereekb.gae.model.crud.services.response;

import com.dereekb.gae.model.crud.services.components.DeleteService;

/**
 * Delete response for a {@link DeleteService}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see DeleteRequest
 */
public interface DeleteResponse<T>
        extends ServiceResponse, SimpleDeleteResponse<T> {

}
