package com.dereekb.gae.model.geo.place.crud;

import com.dereekb.gae.model.crud.deprecated.function.delegate.UpdateFunctionDelegate;
import com.dereekb.gae.model.crud.exception.AttributeFailureException;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.Region;
import com.dereekb.gae.model.geo.place.GeoPlace;


public class GeoPlaceAttributeUpdater
        implements UpdateFunctionDelegate<GeoPlace> {

	@Override
    public void update(GeoPlace template,
                       GeoPlace target) throws AttributeFailureException {

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
