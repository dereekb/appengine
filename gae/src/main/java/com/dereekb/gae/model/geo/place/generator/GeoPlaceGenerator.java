package com.dereekb.gae.model.geo.place.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.Region;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link Generator} for {@link GeoPlace}.
 *
 * @author dereekb
 */
public final class GeoPlaceGenerator extends AbstractModelGenerator<GeoPlace> {

	private Generator<Descriptor> descriptorGenerator;
	private Generator<Point> pointGenerator;
	private Generator<Region> regionGenerator;

	public GeoPlaceGenerator() {
		super(LongModelKeyGenerator.GENERATOR);
	};

	public GeoPlaceGenerator(Generator<ModelKey> keyGenerator) {
		super(keyGenerator);
	};

	public GeoPlaceGenerator(Generator<Descriptor> descriptorGenerator,
	        Generator<Point> pointGenerator,
	        Generator<Region> regionGenerator) {
		this();
		this.descriptorGenerator = descriptorGenerator;
		this.pointGenerator = pointGenerator;
		this.regionGenerator = regionGenerator;
	}

	public Generator<Descriptor> getDescriptorGenerator() {
		return this.descriptorGenerator;
	}

	public void setDescriptorGenerator(Generator<Descriptor> descriptorGenerator) {
		this.descriptorGenerator = descriptorGenerator;
	}

	public Generator<Point> getPointGenerator() {
		return this.pointGenerator;
	}

	public void setPointGenerator(Generator<Point> pointGenerator) {
		this.pointGenerator = pointGenerator;
	}

	public Generator<Region> getRegionGenerator() {
		return this.regionGenerator;
	}

	public void setRegionGenerator(Generator<Region> regionGenerator) {
		this.regionGenerator = regionGenerator;
	}

	@Override
	public GeoPlace generateModel(ModelKey key,
	                              GeneratorArg arg) {
		GeoPlace geoPlace = new GeoPlace();
		geoPlace.setIdentifier(ModelKey.readIdentifier(key));

		if (this.pointGenerator != null) {
			geoPlace.setPoint(this.pointGenerator.generate(arg));
		}

		if (this.regionGenerator != null) {
			geoPlace.setRegion(this.regionGenerator.generate(arg));
		}

		if (this.descriptorGenerator != null) {
			geoPlace.setDescriptor(this.descriptorGenerator.generate(arg));
		}

		return geoPlace;
	}

}
