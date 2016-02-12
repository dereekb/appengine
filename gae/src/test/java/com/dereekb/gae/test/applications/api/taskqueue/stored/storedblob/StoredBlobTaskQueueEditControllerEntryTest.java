package com.dereekb.gae.test.applications.api.taskqueue.stored.storedblob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.test.applications.api.taskqueue.tests.crud.SearchableTaskQueueEditControllerEntryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;


public class StoredBlobTaskQueueEditControllerEntryTest extends SearchableTaskQueueEditControllerEntryTest<StoredBlob> {

	@Override
	@Autowired
	@Qualifier("storedBlobType")
	public void setModelTaskQueueType(String modelTaskQueueType) {
		super.setModelTaskQueueType(modelTaskQueueType);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobSearchIndex")
	public void setSearchIndex(String searchIndex) {
		super.setSearchIndex(searchIndex);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobRegistry")
	public void setGetter(Getter<StoredBlob> getter) {
		super.setGetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredBlob> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobScheduleDeleteTask")
	public void setDeleteTask(ScheduleDeleteTask<StoredBlob> deleteTask) {
		super.setDeleteTask(deleteTask);
	}

}
