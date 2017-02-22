package com.dereekb.gae.client.api.model.crud.request;

import com.dereekb.gae.client.api.model.shared.request.RelatedTypesRequest;
import com.dereekb.gae.model.crud.services.request.ReadRequest;

/**
 * {@link ReadRequest} extension that implements {@link RelatedTypesRequest}.
 * 
 * @author dereekb
 *
 */
public interface ClientReadRequest
        extends ReadRequest, RelatedTypesRequest {

}
