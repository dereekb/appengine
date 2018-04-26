package com.dereekb.gae.test.applications.api.taskqueue.login.key;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleCreateReviewTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleUpdateReviewTask;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.taskqueue.tests.crud.TaskQueueEditControllerEntryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Ignore
@Deprecated
public class LoginKeyTaskQueueEditControllerEntryTest extends TaskQueueEditControllerEntryTest<LoginKey> {

	@Override
	@Autowired
	@Qualifier("loginKeyType")
	public void setModelTaskQueueType(String modelTaskQueueType) {
		super.setModelTaskQueueType(modelTaskQueueType);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyRegistry")
	public void setGetterSetter(GetterSetter<LoginKey> getter) {
		super.setGetterSetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<LoginKey> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyScheduleDeleteTask")
	public void setDeleteTask(ScheduleDeleteTask<LoginKey> deleteTask) {
		super.setDeleteTask(deleteTask);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyScheduleCreateReview")
	public void setScheduleCreateReviewTask(ScheduleCreateReviewTask<LoginKey> scheduleCreateReviewTask) {
		super.setScheduleCreateReviewTask(scheduleCreateReviewTask);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyScheduleUpdateReview")
	public void setScheduleUpdateReviewTask(ScheduleUpdateReviewTask<LoginKey> scheduleUpdateReviewTask) {
		super.setScheduleUpdateReviewTask(scheduleUpdateReviewTask);
	}

}
