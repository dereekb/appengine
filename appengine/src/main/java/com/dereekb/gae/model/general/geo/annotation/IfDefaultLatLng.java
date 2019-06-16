package com.dereekb.gae.model.general.geo.annotation;

import java.lang.reflect.Field;

import com.google.cloud.datastore.LatLng;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.condition.InitializeIf;
import com.googlecode.objectify.condition.ValueIf;

/**
 * Returns true if the input {@link LatLng} has a latitude and longitude of 0.
 *
 * @author dereekb
 *
 */
public class IfDefaultLatLng extends ValueIf<LatLng>
        implements InitializeIf {

	@Override
	public void init(ObjectifyFactory fact,
	                 Field field) {}

	@Override
	public boolean matchesValue(LatLng value) {
		return value != null && value.getLatitude() == 0 && value.getLongitude() == 0;
	}

}
