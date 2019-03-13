package com.dereekb.gae.test.applications.api.taskqueue.stored.storedimageset;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.deprecated.applications.api.taskqueue.tests.crud.SearchableTaskQueueEditControllerEntryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Disabled
@Deprecated
public class StoredImageSetTaskQueueEditControllerEntryTest extends SearchableTaskQueueEditControllerEntryTest<StoredImageSet> {

	@Override
	@Autowired
	@Qualifier("storedImageSetType")
	public void setModelTaskQueueType(String modelTaskQueueType) {
		super.setModelTaskQueueType(modelTaskQueueType);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetSearchIndex")
	public void setSearchIndex(String searchIndex) {
		super.setSearchIndex(searchIndex);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetRegistry")
	public void setGetterSetter(GetterSetter<StoredImageSet> getter) {
		super.setGetterSetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredImageSet> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetScheduleDeleteTask")
	public void setDeleteTask(ScheduleDeleteTask<StoredImageSet> deleteTask) {
		super.setDeleteTask(deleteTask);
	}

}
