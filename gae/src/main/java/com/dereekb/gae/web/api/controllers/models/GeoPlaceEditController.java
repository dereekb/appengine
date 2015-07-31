package com.dereekb.gae.web.api.controllers.models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.dto.GeoPlaceData;
import com.dereekb.gae.web.api.model.controller.EditModelController;
import com.dereekb.gae.web.api.model.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.controller.EditModelControllerDelegate;

@RestController
@RequestMapping("/geoplace")
public class GeoPlaceEditController extends EditModelController<GeoPlace, GeoPlaceData> {

	public GeoPlaceEditController(EditModelControllerDelegate<GeoPlace> delegate,
	        EditModelControllerConversionDelegate<GeoPlace, GeoPlaceData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
