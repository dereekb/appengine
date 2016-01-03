package com.dereekb.gae.test.applications.api.taskqueue.geoplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.test.applications.api.taskqueue.tests.crud.SearchableTaskQueueEditControllerEntryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;


public class GeoPlaceTaskQueueEditControllerEntryTest extends SearchableTaskQueueEditControllerEntryTest<GeoPlace> {

	@Override
	@Autowired
	@Qualifier("geoPlaceSearchIndex")
	public void setSearchIndex(String searchIndex) {
		super.setSearchIndex(searchIndex);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceRegistry")
	public void setGetter(Getter<GeoPlace> getter) {
		super.setGetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<GeoPlace> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
