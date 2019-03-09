package com.dereekb.gae.web.api.model.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.dto.GeoPlaceData;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerDelegate;

@RestController
@RequestMapping("/geoplace")
public class GeoPlaceEditController extends EditModelController<GeoPlace, GeoPlaceData> {

	public GeoPlaceEditController(EditModelControllerDelegate<GeoPlace> delegate,
	        EditModelControllerConversionDelegate<GeoPlace, GeoPlaceData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
