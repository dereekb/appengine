package com.dereekb.gae.web.api.auth.controller.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.misc.keyed.Keyed;
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

	public ApiLoginTokenModelContextTypeImpl() {};

	public ApiLoginTokenModelContextTypeImpl(ApiLoginTokenModelContextType type) {
		super(type.getModelType());
		this.setKeys(type.getKeys());
	}

	public ApiLoginTokenModelContextTypeImpl(String modelType, Set<String> keys) {
		super(modelType);
		this.setKeys(keys);
	}

	public static ApiLoginTokenModelContextTypeImpl fromKeyed(String modelType, Keyed<ModelKey> keyed) {
		String key = ModelKey.readStringKey(keyed);
		return new ApiLoginTokenModelContextTypeImpl(modelType, SetUtility.wrap(key));
	}

	public static ApiLoginTokenModelContextTypeImpl fromKeyed(String modelType,
	                                                          Iterable<? extends Keyed<ModelKey>> keyed) {
		Set<String> keys = ModelKey.readStringKeysSet(keyed);
		return new ApiLoginTokenModelContextTypeImpl(modelType, keys);
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
