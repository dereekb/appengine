package com.dereekb.gae.model.general.geo.generation;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.general.geo.MapPoint;
import com.dereekb.gae.model.general.geo.Point;

/**
 * {@link Generator} for {@link MapPoint}.
 *
 * @author dereekb
 *
 */
public class MapPointGenerator extends AbstractGenerator<MapPoint> {

	private Generator<Point> pointGenerator = PointGenerator.GENERATOR;

	public MapPointGenerator() {}

	public Generator<Point> getPointGenerator() {
		return this.pointGenerator;
	}

	public void setPointGenerator(Generator<Point> pointGenerator) {
		this.pointGenerator = pointGenerator;
	}

	@Override
	public MapPoint generate(GeneratorArg arg) {
		MapPoint point = new MapPoint();

		point.setPoint(this.pointGenerator.generate(arg));
		// TODO: Add generators for other types.

		return point;
	}

	@Override
	public String toString() {
		return "PointGenerator []";
	}

}
