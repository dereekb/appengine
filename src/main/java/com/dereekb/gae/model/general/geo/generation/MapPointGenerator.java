package com.dereekb.gae.model.general.geo.generation;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.general.geo.Address;
import com.dereekb.gae.model.general.geo.MapPoint;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.utilities.misc.random.IntegerGenerator;

/**
 * {@link Generator} for {@link MapPoint}.
 *
 * @author dereekb
 *
 */
public class MapPointGenerator extends AbstractGenerator<MapPoint> {

	private static final IntegerGenerator ZOOM_GENERATOR = new IntegerGenerator(MapPoint.MIN_ZOOM, MapPoint.MAX_ZOOM);

	private Generator<Point> pointGenerator = PointGenerator.GENERATOR;
	private Generator<Address> addressGenerator;

	public MapPointGenerator() {}

	public Generator<Point> getPointGenerator() {
		return this.pointGenerator;
	}

	public void setPointGenerator(Generator<Point> pointGenerator) {
		this.pointGenerator = pointGenerator;
	}

	public Generator<Address> getAddressGenerator() {
		return this.addressGenerator;
	}

	public void setAddressGenerator(Generator<Address> addressGenerator) {
		this.addressGenerator = addressGenerator;
	}

	@Override
	public MapPoint generate(GeneratorArg arg) {
		MapPoint point = new MapPoint();

		point.setZoom(ZOOM_GENERATOR.generate(arg));
		point.setPoint(this.pointGenerator.generate(arg));

		if (this.addressGenerator != null) {
			point.setAddress(this.addressGenerator.generate(arg));
		}

		return point;
	}

	@Override
	public String toString() {
		return "PointGenerator []";
	}

}
