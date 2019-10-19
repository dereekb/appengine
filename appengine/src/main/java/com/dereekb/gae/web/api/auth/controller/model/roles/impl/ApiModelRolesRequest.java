package com.dereekb.gae.web.api.auth.controller.model.roles.impl;

import java.util.List;

import com.dereekb.gae.web.api.shared.request.AtomicApiRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ApiModelRolesRequest extends AtomicApiRequest<ApiModelRolesTypedKeysRequestImpl> {

	@JsonIgnore
	public void setContexts(List<ApiModelRolesTypedKeysRequest> data) throws IllegalArgumentException {
		List<ApiModelRolesTypedKeysRequestImpl> copies = ApiModelRolesTypedKeysRequestImpl.copy(data);
		this.setData(copies);
	}

	@Override
	public String toString() {
		return "ApiModelRolesRequest [atomic=" + this.atomic + ", data=" + this.data + "]";
	}

}
