package com.dereekb.gae.model.extension.search.query.parameters;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.datastore.models.owner.OwnedDatabaseModel;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.StringQueryFieldParameter;

/**
 * Used by queries that query {@link OwnedDatabaseModel} types.
 * 
 * @author dereekb
 *
 */
public class AbstractOwnedModelQuery
        implements ConfigurableEncodedQueryParameters {

	public static final String DEFAULT_OWNER_ID_FIELD = "ownerId";

	private StringQueryFieldParameter ownerId;

	public StringQueryFieldParameter getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerIdParameter) {
		this.ownerId = StringQueryFieldParameter.make(this.getOwnerIdField(), ownerIdParameter);
	}

	public void setOwnerId(StringQueryFieldParameter ownerId) {
		this.ownerId = StringQueryFieldParameter.make(this.getOwnerIdField(), ownerId);
	}

	protected String getOwnerIdField() {
		return DEFAULT_OWNER_ID_FIELD;
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		ParameterUtility.put(parameters, this.ownerId);
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		this.setOwnerIdParameters(parameters);
	}

	protected void setOwnerIdParameters(Map<String, String> parameters) {
		this.setOwnerId(parameters.get(this));
	}

}
