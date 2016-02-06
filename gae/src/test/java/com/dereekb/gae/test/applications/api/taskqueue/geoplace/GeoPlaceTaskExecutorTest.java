package com.dereekb.gae.test.applications.api.taskqueue.geoplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.iterate.impl.IterateTaskExecutorFactoryImpl;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.test.applications.api.taskqueue.tests.extension.iterate.IterateTaskExecutorTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;


public class GeoPlaceTaskExecutorTest extends IterateTaskExecutorTest<GeoPlace> {

	@Override
	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<GeoPlace> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceIterateTaskExecutorFactory")
	public void setFactory(IterateTaskExecutorFactoryImpl<GeoPlace> factory) {
		super.setFactory(factory);
	}

}
