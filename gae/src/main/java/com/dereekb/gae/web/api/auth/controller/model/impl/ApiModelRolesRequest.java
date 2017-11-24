package com.dereekb.gae.web.api.auth.controller.model.impl;

import com.dereekb.gae.web.api.shared.request.AtomicApiRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * API Request for model roles.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiModelRolesRequest extends AtomicApiRequest<ApiLoginTokenModelContextTypeImpl> {

	@Override
	public String toString() {
		return "ApiModelRolesRequest [atomic=" + this.atomic + ", data=" + this.data + "]";
	}

}
