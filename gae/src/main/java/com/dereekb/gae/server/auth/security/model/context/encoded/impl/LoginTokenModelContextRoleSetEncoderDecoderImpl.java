package com.dereekb.gae.server.auth.security.model.context.encoded.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRole;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRoleSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextRoleSetEncoderDecoder;
import com.dereekb.gae.utilities.collections.set.dencoder.impl.AbstractEncodedLongDencoderImpl;

/**
 * {@link LoginTokenModelContextRoleSetEncoderDecoder} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextRoleSetEncoderDecoderImpl extends AbstractEncodedLongDencoderImpl<LoginTokenModelContextRole>
        implements LoginTokenModelContextRoleSetEncoderDecoder {

	private Map<String, Integer> reverseMap;

	public LoginTokenModelContextRoleSetEncoderDecoderImpl(Map<Integer, LoginTokenModelContextRole> map)
	        throws IllegalArgumentException {
		super(map);
	}

	@Override
	public void setMap(Map<Integer, LoginTokenModelContextRole> map) throws IllegalArgumentException {
		super.setMap(map);

		Map<String, Integer> reverseMap = new HashMap<String, Integer>();

		for (Entry<Integer, LoginTokenModelContextRole> entry : map.entrySet()) {
			String role = entry.getValue().getRole();
			Integer value = entry.getKey();
			reverseMap.put(role, value);
		}

		this.reverseMap = reverseMap;
	}

	// MARK: AbstractEncodedLongDencoderImpl
	@Override
	protected int getIndexForValue(LoginTokenModelContextRole value) {
		String role = value.getRole();
		return this.reverseMap.get(role);
	}

	// MARK: LoginTokenModelContextRoleSetEncoderDecoder
	@Override
	public String encodeRoleSet(LoginTokenModelContextRoleSet roleSet) {
		Set<LoginTokenModelContextRole> roles = roleSet.getRoles();
		return this.encodeLong(roles).toString();
	}

	@Override
	public Set<LoginTokenModelContextRole> decodeRoleSet(String encodedRoleSet) {
		Long encodedLong = new Long(encodedRoleSet);
		return this.decode(encodedLong);
	}

	@Override
	public String toString() {
		return "LoginTokenModelContextRoleSetEncoderDecoderImpl [reverseMap=" + this.reverseMap + "]";
	}

}
