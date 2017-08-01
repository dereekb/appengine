package com.dereekb.gae.test.applications.api.taskqueue.stored.storedimage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.applications.api.taskqueue.tests.crud.SearchableTaskQueueEditControllerEntryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.googlecode.objectify.Key;

public class StoredImageTaskQueueEditControllerEntryTest extends SearchableTaskQueueEditControllerEntryTest<StoredImage> {

	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	private TestModelGenerator<StoredBlob> storedBlobGenerator;

	@Override
	@Autowired
	@Qualifier("storedImageType")
	public void setModelTaskQueueType(String modelTaskQueueType) {
		super.setModelTaskQueueType(modelTaskQueueType);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSearchIndex")
	public void setSearchIndex(String searchIndex) {
		super.setSearchIndex(searchIndex);
	}

	@Override
	@Autowired
	@Qualifier("storedImageRegistry")
	public void setGetterSetter(GetterSetter<StoredImage> getter) {
		super.setGetterSetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredImage> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Autowired
	@Qualifier("storedImageScheduleDeleteTask")
	public void setDeleteTask(ScheduleDeleteTask<StoredImage> deleteTask) {
		super.setDeleteTask(deleteTask);
	}

	@Override
	protected void removeRelated(StoredImage model,
	                             Setter<StoredImage> setter) {
		model.setStoredBlob(null);
	}

	@Override
	protected void createRelated(StoredImage model) {
		Key<StoredBlob> storedBlobKey = model.getStoredBlob();

		if (storedBlobKey != null) {
			ModelKey storedBlobModelKey = new ModelKey(storedBlobKey.getId());
			this.storedBlobGenerator.generateModel(storedBlobModelKey);
		}

	}

}
