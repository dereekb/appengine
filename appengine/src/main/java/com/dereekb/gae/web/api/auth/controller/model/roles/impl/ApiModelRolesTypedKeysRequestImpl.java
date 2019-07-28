package com.dereekb.gae.web.api.auth.controller.model.roles.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.misc.keyed.Keyed;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * {@link ApiModelRolesTypedKeysRequest} implementation.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiModelRolesTypedKeysRequestImpl extends TypedModelImpl
        implements ApiModelRolesTypedKeysRequest {

	@NotEmpty
	private Set<String> keys;

	public ApiModelRolesTypedKeysRequestImpl() {};

	public ApiModelRolesTypedKeysRequestImpl(ApiModelRolesTypedKeysRequest type) {
		super(type.getModelType());
		this.setKeys(type.getKeys());
	}

	public ApiModelRolesTypedKeysRequestImpl(String modelType, Set<String> keys) {
		super(modelType);
		this.setKeys(keys);
	}

	public static ApiModelRolesTypedKeysRequestImpl fromKeyed(String modelType, Keyed<ModelKey> keyed) {
		String key = ModelKey.readStringKey(keyed);
		return new ApiModelRolesTypedKeysRequestImpl(modelType, SetUtility.wrap(key));
	}

	public static ApiModelRolesTypedKeysRequestImpl fromKeyed(String modelType,
	                                                          Iterable<? extends Keyed<ModelKey>> keyed) {
		Set<String> keys = ModelKey.readStringKeysSet(keyed);
		return new ApiModelRolesTypedKeysRequestImpl(modelType, keys);
	}

	public static List<ApiModelRolesTypedKeysRequestImpl> copy(List<ApiModelRolesTypedKeysRequest> data) {
		List<ApiModelRolesTypedKeysRequestImpl> copies = new ArrayList<ApiModelRolesTypedKeysRequestImpl>();

		for (ApiModelRolesTypedKeysRequest entry : data) {
			copies.add(new ApiModelRolesTypedKeysRequestImpl(entry));
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
		return "ApiModelRolesTypedKeysRequestImpl [keys=" + this.keys + ", modelType=" + this.modelType + "]";
	}

}
