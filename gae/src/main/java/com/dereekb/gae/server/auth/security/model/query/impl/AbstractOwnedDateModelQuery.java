package com.dereekb.gae.server.auth.security.model.query.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.search.query.parameters.AbstractDateModelQuery;
import com.dereekb.gae.server.auth.security.model.query.MutableOwnedModelQuery;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.impl.StringQueryFieldParameter;

/**
 * Used by queries that need to query both the owner and a date.
 * 
 * @author dereekb
 * @see {@link AbstractDateModelQuery}.
 */
public class AbstractOwnedDateModelQuery extends AbstractDateModelQuery
        implements MutableOwnedModelQuery {

	private StringQueryFieldParameter ownerId;

	public AbstractOwnedDateModelQuery() {}

	public AbstractOwnedDateModelQuery(Map<String, String> parameters) throws IllegalArgumentException {
		this.setParameters(parameters);
	}

	public StringQueryFieldParameter getOwnerId() {
		return this.ownerId;
	}

	@Override
	public void setOwnerId(String ownerIdParameter) {
		this.ownerId = StringQueryFieldParameter.make(this.getOwnerIdField(), ownerIdParameter);
	}

	@Override
	public void setOwnerId(StringQueryFieldParameter ownerId) {
		this.ownerId = StringQueryFieldParameter.make(this.getOwnerIdField(), ownerId);
	}

	@Override
	public void setEqualsOwnerId(String ownerId) throws IllegalArgumentException {
		this.ownerId = StringQueryFieldParameter.makeEqualsQuery(this.getOwnerIdField(), ownerId);
	}

	protected String getOwnerIdField() {
		return AbstractOwnedModelQuery.DEFAULT_OWNER_ID_FIELD;
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = super.getParameters();
		ParameterUtility.put(parameters, this.ownerId);
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		super.setParameters(parameters);
		this.setOwnerIdParameters(parameters);
	}

	protected void setOwnerIdParameters(Map<String, String> parameters) {
		this.setOwnerId(parameters.get(this));
	}

}
