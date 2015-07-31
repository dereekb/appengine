package com.dereekb.gae.web.api.controllers.models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.web.api.model.controller.ReadModelController;
import com.dereekb.gae.web.api.model.controller.ReadModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.controller.ReadModelControllerDelegate;

@RestController
@RequestMapping("/geoplace")
public class GeoPlaceReadController extends ReadModelController<GeoPlace> {

	public GeoPlaceReadController(ReadModelControllerDelegate<GeoPlace> delegate,
	        ReadModelControllerConversionDelegate<GeoPlace> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
