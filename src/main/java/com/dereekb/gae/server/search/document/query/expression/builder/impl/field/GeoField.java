package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.AbstractField;

public abstract class GeoField extends AbstractField {

	protected static final String DEGREES_DOUBLE_FORMAT = "0.000000";
	protected static final String GEO_POINT_FORMAT = "geopoint(%s, %s)";

	protected Point point;

	public GeoField(String name, Point point) {
		super(name);
		this.point = point;
	}

	public Point getPoint() {
		return this.point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public String getGeoPointString() {
		NumberFormat formatter = new DecimalFormat(DEGREES_DOUBLE_FORMAT);
		String latitudeString = formatter.format(this.point.getLatitude());
		String longitudeString = formatter.format(this.point.getLongitude());

		String geoPoint = String.format(GEO_POINT_FORMAT, latitudeString, longitudeString);
		return geoPoint;
	}

	@Override
	public String toString() {
		return "GeoField [point=" + this.point + ", name=" + this.name + "]";
	}

}
