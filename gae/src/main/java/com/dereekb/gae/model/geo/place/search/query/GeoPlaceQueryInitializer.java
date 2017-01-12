package com.dereekb.gae.model.geo.place.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyKeyFieldParameterBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyKeyFieldParameterBuilder.ObjectifyKeyFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableQueryParameters;
import com.googlecode.objectify.Key;

/**
 * {@link ConfigurableQueryParameters} implementation for a
 * {@link GeoPlaceQuery}.
 *
 * @author dereekb
 *
 */
public class GeoPlaceQueryInitializer
        implements ObjectifyQueryRequestLimitedBuilderInitializer {

	public static final String PARENT_FIELD = "parent";

	private static final ObjectifyKeyFieldParameterBuilder<GeoPlace> PARENT_BUILDER = new ObjectifyKeyFieldParameterBuilder<GeoPlace>(
	        ModelKeyType.NUMBER, GeoPlace.class);

	@Override
	public void initalizeBuilder(ObjectifyQueryRequestLimitedBuilder builder,
	                             Map<String, String> parameters) {

		GeoPlaceQuery query = new GeoPlaceQuery();

		if (parameters != null) {
			query.setParameters(parameters);
			query.configure(builder);
		}

	}

	public static class GeoPlaceQuery
	        implements ConfigurableQueryParameters {

		private ObjectifyKeyFieldParameter<GeoPlace> parent;

		public ObjectifyKeyFieldParameter<GeoPlace> getParent() {
			return this.parent;
		}

		public void setParent(Key<GeoPlace> parent) {
			if (parent != null) {
				this.parent = PARENT_BUILDER.make(PARENT_FIELD, parent);
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
				this.parent = PARENT_BUILDER.make(PARENT_FIELD, parentString);
			} else {
				this.parent = null;
			}

		}

		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {

			if (this.parent != null) {
				this.parent.configure(request);
			}

		}

	}

}
