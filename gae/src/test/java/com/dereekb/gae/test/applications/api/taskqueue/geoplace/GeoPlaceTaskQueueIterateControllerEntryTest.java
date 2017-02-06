package com.dereekb.gae.test.applications.api.taskqueue.geoplace;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.taskqueue.tests.crud.TaskQueueIterateControllerEntryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class GeoPlaceTaskQueueIterateControllerEntryTest extends TaskQueueIterateControllerEntryTest<GeoPlace> {

	@Autowired
	@Qualifier("geoPlaceClearParentsIterateTaskKey")
	private String clearParentsIterateKey;

	@Override
	@Autowired
	@Qualifier("geoPlaceType")
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<GeoPlace> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Autowired
	@Qualifier("geoPlaceRegistry")
	public ObjectifyRegistry<GeoPlace> geoPlaceRegistry;

	@Override
	@Autowired
	@Qualifier("geoPlaceRegistry")
	public void setFactory(ObjectifyRegistry<GeoPlace> factory) {
		super.setFactory(factory);
	}

	@Test
	public void testClearParent() {
		new UnlinkParentsTest().test();
	}

	private class UnlinkParentsTest extends TaskQueueIterateTest {

		public UnlinkParentsTest() {
			super(GeoPlaceTaskQueueIterateControllerEntryTest.this.clearParentsIterateKey);
		}

		@Override
		protected List<GeoPlace> generate() {
			List<GeoPlace> generated = super.generate();

			// Check each model has a parent.
			for (GeoPlace model : generated) {
				Assert.assertNotNull(model.getParent());
			}

			return generated;
		}

		@Override
		protected List<GeoPlace> reloadModels(List<GeoPlace> input) {
			return GeoPlaceTaskQueueIterateControllerEntryTest.this.geoPlaceRegistry.get(input);
		}

		@Override
		protected void checkResults(List<GeoPlace> models) {
			for (GeoPlace model : models) {
				Assert.assertNull(model.getParent());
			}
		}

	}

}
