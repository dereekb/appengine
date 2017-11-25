package com.dereekb.gae.web.api.auth.controller.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;
import com.dereekb.gae.utilities.collections.list.SetUtility;
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

	public ApiLoginTokenModelContextTypeImpl(ApiLoginTokenModelContextType type) {
		this.setKeys(type.getKeys());
	}

	public ApiLoginTokenModelContextTypeImpl(Set<String> keys) {
		this.setKeys(keys);
	}

	public static List<ApiLoginTokenModelContextTypeImpl> copy(List<ApiLoginTokenModelContextType> data) {
		List<ApiLoginTokenModelContextTypeImpl> copies = new ArrayList<ApiLoginTokenModelContextTypeImpl>();

		for (ApiLoginTokenModelContextType entry : data) {
			copies.add(new ApiLoginTokenModelContextTypeImpl(entry));
		}

		return copies;
	}

	// MARK: ApiLoginTokenModelContextType
	@Override
	public Set<String> getKeys() {
		return this.keys;
	}

	public void setKeys(Set<String> keys) {
		this.keys = SetUtility.copy(keys);
	}

	@Override
	public String toString() {
		return "ApiLoginTokenModelContextTypeImpl [keys=" + this.keys + ", modelType=" + this.modelType + "]";
	}

}
