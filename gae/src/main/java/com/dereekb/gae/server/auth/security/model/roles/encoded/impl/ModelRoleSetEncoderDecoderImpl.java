package com.dereekb.gae.server.auth.security.model.roles.encoded.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.encoded.ModelRoleSetEncoderDecoder;
import com.dereekb.gae.utilities.collections.set.dencoder.impl.AbstractEncodedLongDencoderImpl;

/**
 * {@link ModelRoleSetEncoderDecoder} implementation.
 *
 * @author dereekb
 *
 */
public class ModelRoleSetEncoderDecoderImpl extends AbstractEncodedLongDencoderImpl<ModelRole>
        implements ModelRoleSetEncoderDecoder {

	private Map<String, Integer> reverseMap;

	public ModelRoleSetEncoderDecoderImpl(Map<Integer, ModelRole> map)
	        throws IllegalArgumentException {
		super(map);
	}

	@Override
	public void setMap(Map<Integer, ModelRole> map) throws IllegalArgumentException {
		super.setMap(map);

		Map<String, Integer> reverseMap = new HashMap<String, Integer>();

		for (Entry<Integer, ModelRole> entry : map.entrySet()) {
			String role = entry.getValue().getRole();
			Integer value = entry.getKey();
			reverseMap.put(role, value);
		}

		this.reverseMap = reverseMap;
	}

	// MARK: AbstractEncodedLongDencoderImpl
	@Override
	protected int getIndexForValue(ModelRole value) {
		String role = value.getRole();
		return this.reverseMap.get(role);
	}

	// MARK: ModelRoleSetEncoderDecoder
	@Override
	public String encodeRoleSet(ModelRoleSet roleSet) {
		Collection<ModelRole> roles = roleSet.getRoles();
		return this.encodeLong(roles).toString();
	}

	@Override
	public Set<ModelRole> decodeRoleSet(String encodedRoleSet) {
		Long encodedLong = new Long(encodedRoleSet);
		return this.decode(encodedLong);
	}

	@Override
	public String toString() {
		return "ModelRoleSetEncoderDecoderImpl [reverseMap=" + this.reverseMap + "]";
	}

}
