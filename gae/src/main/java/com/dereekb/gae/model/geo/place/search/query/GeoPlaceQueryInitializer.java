package com.dereekb.gae.model.geo.place.search.query;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyKeyFieldParameterBuilder;
import com.googlecode.objectify.Key;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link GeoPlaceQuery}.
 *
 * @author dereekb
 *
 */
public class GeoPlaceQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl {

	private static final ObjectifyKeyFieldParameterBuilder<GeoPlace> PARENT_BUILDER = ObjectifyKeyFieldParameterBuilder
	        .make(ModelKeyType.NUMBER, GeoPlace.class);

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyGeoPlaceQuery();
	}

	public static class ObjectifyGeoPlaceQuery extends GeoPlaceQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public void setParent(Key<GeoPlace> parent) {
			this.setParent(PARENT_BUILDER.getUtil().toModelKey(parent));
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			PARENT_BUILDER.configure(request, this.getParent());
		}

	}

}
