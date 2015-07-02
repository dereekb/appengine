package com.dereekb.gae.model.geo.place.generator;

import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.Region;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Implementation of {@link Generator} for {@link GeoPlace}.
 *
 * @author dereekb
 */
public final class GeoPlaceGenerator extends AbstractModelGenerator<GeoPlace> {

	private Factory<String> infoTypeFactory;
	private Factory<Point> pointFactory;
	private Factory<Region> regionFactory;

	public GeoPlaceGenerator() {};

	public GeoPlaceGenerator(Factory<String> infoTypeFactory, Factory<Point> pointFactory, Factory<Region> regionFactory) {
		this.infoTypeFactory = infoTypeFactory;
		this.pointFactory = pointFactory;
		this.regionFactory = regionFactory;
	}

	public Factory<String> getInfoTypeFactory() {
		return this.infoTypeFactory;
	}

	public void setInfoTypeFactory(Factory<String> infoTypeFactory) {
		this.infoTypeFactory = infoTypeFactory;
	}

	public Factory<Point> getPointFactory() {
		return this.pointFactory;
	}

	public void setPointFactory(Factory<Point> pointFactory) {
		this.pointFactory = pointFactory;
	}

	public Factory<Region> getRegionFactory() {
		return this.regionFactory;
	}

	public void setRegionFactory(Factory<Region> regionFactory) {
		this.regionFactory = regionFactory;
	}

	@Override
	public GeoPlace generateModel(ModelKey key) {
		GeoPlace geoPlace = new GeoPlace();

		if (key != null) {
			Long id = key.getId();
			geoPlace.setIdentifier(id);
		}

		if (this.pointFactory != null) {
			geoPlace.setPoint(this.pointFactory.make());
		}

		if (this.regionFactory != null) {
			geoPlace.setRegion(this.regionFactory.make());
		}

		if (this.infoTypeFactory != null) {
			String infoType = this.infoTypeFactory.make();
			geoPlace.setInfoType(infoType);
			geoPlace.setInfoIdentifier(this.randomPositiveLong().toString());
		}

		return geoPlace;
	}

}
