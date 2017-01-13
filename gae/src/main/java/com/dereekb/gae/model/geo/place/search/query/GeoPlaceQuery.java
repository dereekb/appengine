package com.dereekb.gae.model.geo.place.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Utility used for querying a {@link GeoPlace}.
 * 
 * @author dereekb
 *
 */
public class GeoPlaceQuery
        implements ConfigurableQueryParameters {

	public static final String PARENT_FIELD = "parent";

	private static final ModelKeyQueryFieldParameterBuilder PARENT_FIELD_BUILDER = ModelKeyQueryFieldParameterBuilder.NUMBER_SINGLETON;

	private ModelKeyQueryFieldParameter parent;

	public ModelKeyQueryFieldParameter getParent() {
		return this.parent;
	}

	public void setParent(ModelKey login) {
		if (login != null) {
			this.parent = PARENT_FIELD_BUILDER.make(PARENT_FIELD, login);
		} else {
			this.parent = null;
		}
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();

		if (this.parent != null) {
			parameters.put(PARENT_FIELD, this.parent.getParameterString());
		}

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		String parentString = parameters.get(PARENT_FIELD);

		if (parentString != null) {
			this.parent = PARENT_FIELD_BUILDER.makeModelKeyParameter(PARENT_FIELD, parentString);
		} else {
			this.parent = null;
		}

	}

}
