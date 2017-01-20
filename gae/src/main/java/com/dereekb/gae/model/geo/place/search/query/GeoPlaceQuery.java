package com.dereekb.gae.model.geo.place.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.utility.ParameterUtility;

/**
 * Utility used for querying a {@link GeoPlace}.
 * 
 * @author dereekb
 *
 */
public class GeoPlaceQuery
        implements ConfigurableEncodedQueryParameters {

	public static final String PARENT_FIELD = "parent";

	private static final ModelKeyQueryFieldParameterBuilder PARENT_FIELD_BUILDER = ModelKeyQueryFieldParameterBuilder.NUMBER_SINGLETON;

	private ModelKeyQueryFieldParameter parent;

	public ModelKeyQueryFieldParameter getParent() {
		return this.parent;
	}

	public void setParent(ModelKey parent) {
		this.parent = PARENT_FIELD_BUILDER.make(PARENT_FIELD, parent);
	}

	public void setParent(ModelKeyQueryFieldParameter parent) {
		this.parent = PARENT_FIELD_BUILDER.make(PARENT_FIELD, parent);
	}

	public void setParent(String parent) {
		this.parent = PARENT_FIELD_BUILDER.makeModelKeyParameter(PARENT_FIELD, parent);
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();

		ParameterUtility.put(parameters, this.parent);

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		this.setParent(parameters.get(PARENT_FIELD));
	}

}
