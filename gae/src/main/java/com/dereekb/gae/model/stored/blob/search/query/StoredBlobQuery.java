package com.dereekb.gae.model.stored.blob.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.IntegerQueryFieldParameter;

/**
 * Utility used for querying a {@link LoginPointer}.
 * 
 * @author dereekb
 *
 */
public class StoredBlobQuery
        implements ConfigurableEncodedQueryParameters {

	public static final String TYPE_FIELD = "type";

	private IntegerQueryFieldParameter type;

	public IntegerQueryFieldParameter getType() {
		return this.type;
	}

	public void setType(LoginPointerType type) {
		Integer typeInteger = null;

		if (type != null) {
			typeInteger = type.id;
		}

		this.setType(typeInteger);
	}

	public void setType(String type) {
		this.type = IntegerQueryFieldParameter.make(TYPE_FIELD, type);
	}

	public void setType(Integer type) {
		this.type = IntegerQueryFieldParameter.make(TYPE_FIELD, type);
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		ParameterUtility.put(parameters, this.type);
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		this.setType(parameters.get(TYPE_FIELD));
	}

}
