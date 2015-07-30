package com.dereekb.gae.model.geo.place.crud;

import com.dereekb.gae.model.crud.exception.AttributeFailureException;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.Region;
import com.dereekb.gae.model.geo.place.GeoPlace;

public class GeoPlaceAttributeUpdater
        implements UpdateTaskDelegate<GeoPlace> {

	@Override
	public void updateTarget(GeoPlace target,
	                         GeoPlace template) throws AttributeFailureException {
		Point point = template.getPoint();

		if (point != null) {
			target.setPoint(point);
		}

		Region region = template.getRegion();

		if (region != null) {
			target.setRegion(region);
		}

	}

}
