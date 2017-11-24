package com.dereekb.gae.web.api.auth.controller.model.impl;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;
import com.dereekb.gae.web.api.auth.controller.model.ApiLoginTokenModelContextType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * {@link ApiLoginTokenModelContextType} implementation.
 * 
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiLoginTokenModelContextTypeImpl extends TypedModelImpl
        implements ApiLoginTokenModelContextType {

	@NotEmpty
	private Set<String> keys;

	// MARK: ApiLoginTokenModelContextType
	@Override
	public Set<String> getKeys() {
		return this.keys;
	}

	public void setKeys(Set<String> keys) {
		this.keys = keys;
	}

	@Override
	public String toString() {
		return "ApiLoginTokenModelContextTypeImpl [keys=" + this.keys + ", modelType=" + this.modelType + "]";
	}

}
