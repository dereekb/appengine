package com.dereekb.gae.server.auth.security.model.roles.encoded.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.roles.IndexCodedModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.encoded.ModelRoleSetEncoderDecoder;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.set.dencoder.impl.AbstractEncodedLongDencoderImpl;
import com.dereekb.gae.utilities.misc.keyed.utility.KeyedUtility;

/**
 * {@link ModelRoleSetEncoderDecoder} implementation.
 *
 * @author dereekb
 *
 */
public class ModelRoleSetEncoderDecoderImpl extends AbstractEncodedLongDencoderImpl<ModelRole>
        implements ModelRoleSetEncoderDecoder {

	private Map<String, Integer> reverseMap;

	public ModelRoleSetEncoderDecoderImpl(Map<Integer, ModelRole> map) throws IllegalArgumentException {
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

	// MARK: Builder
	public static ModelRoleSetEncoderDecoderBuilder makeBuilder() {
		return new ModelRoleSetEncoderDecoderBuilderImpl();
	}

	public static ModelRoleSetEncoderDecoderBuilder makeBuilder(IndexCodedModelRole[]... roles) {
		return new ModelRoleSetEncoderDecoderBuilderImpl(roles);
	}

	public static interface ModelRoleSetEncoderDecoderBuilder {

		public void addRoles(IndexCodedModelRole[] roles);

		public void addRoles(IndexCodedModelRole[]... roles);

		public ModelRoleSetEncoderDecoder build();

	}

	private static class ModelRoleSetEncoderDecoderBuilderImpl
	        implements ModelRoleSetEncoderDecoderBuilder {

		private final List<IndexCodedModelRole> rolesList = new ArrayList<IndexCodedModelRole>();

		public ModelRoleSetEncoderDecoderBuilderImpl() {}

		public ModelRoleSetEncoderDecoderBuilderImpl(IndexCodedModelRole[]... roles) {
			super();
			this.addRoles(roles);
		}

		// MARK: ModelRoleSetEncoderDecoderBuilder
		@Override
		public void addRoles(IndexCodedModelRole[] roles) {
			this.rolesList.addAll(ListUtility.flatten(roles));
		}

		@Override
		public void addRoles(IndexCodedModelRole[]... roles) {
			this.rolesList.addAll(ListUtility.flatten(roles));
		}

		@Override
		public ModelRoleSetEncoderDecoder build() {
			Map<Integer, ? extends ModelRole> rolesMap = makeIndexedRolesMap(this.rolesList);
			Map<Integer, ModelRole> map = new HashMap<Integer, ModelRole>(rolesMap);
			ModelRoleSetEncoderDecoderImpl dencoder = new ModelRoleSetEncoderDecoderImpl(map);
			return dencoder;
		}

		public static Map<Integer, ? extends ModelRole> makeIndexedRolesMap(Iterable<IndexCodedModelRole> roles) {
			return KeyedUtility.makeCodedMap(roles);
		}

	}

	@Override
	public String toString() {
		return "ModelRoleSetEncoderDecoderImpl [reverseMap=" + this.reverseMap + "]";
	}

}
