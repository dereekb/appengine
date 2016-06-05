package com.dereekb.gae.test.applications.api.taskqueue.geoplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.taskqueue.tests.crud.SearchableTaskQueueEditControllerEntryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;


public class GeoPlaceTaskQueueEditControllerEntryTest extends SearchableTaskQueueEditControllerEntryTest<GeoPlace> {

	@Override
	@Autowired
	@Qualifier("geoPlaceType")
	public void setModelTaskQueueType(String modelTaskQueueType) {
		super.setModelTaskQueueType(modelTaskQueueType);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceSearchIndex")
	public void setSearchIndex(String searchIndex) {
		super.setSearchIndex(searchIndex);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceRegistry")
	public void setGetterSetter(GetterSetter<GeoPlace> getter) {
		super.setGetterSetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<GeoPlace> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceScheduleDeleteTask")
	public void setDeleteTask(ScheduleDeleteTask<GeoPlace> deleteTask) {
		super.setDeleteTask(deleteTask);
	}

}