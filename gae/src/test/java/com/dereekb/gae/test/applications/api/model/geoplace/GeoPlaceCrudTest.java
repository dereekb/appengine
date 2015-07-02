package com.dereekb.gae.test.applications.api.model.geoplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.model.tests.crud.CrudServiceTester;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class GeoPlaceCrudTest extends CrudServiceTester<GeoPlace> {

	@Override
	@Autowired
	@Qualifier("geoPlaceCrudService")
	public void setService(CrudService<GeoPlace> service) {
		super.setService(service);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceRegistry")
	public void setGetterSetter(GetterSetter<GeoPlace> getterSetter) {
		super.setGetterSetter(getterSetter);
	}

	@Override
    @Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<GeoPlace> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
