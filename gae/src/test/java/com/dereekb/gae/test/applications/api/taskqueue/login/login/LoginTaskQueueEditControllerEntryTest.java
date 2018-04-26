package com.dereekb.gae.test.applications.api.taskqueue.login.login;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleCreateReviewTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleUpdateReviewTask;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.taskqueue.tests.crud.TaskQueueEditControllerEntryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Ignore
@Deprecated
public class LoginTaskQueueEditControllerEntryTest extends TaskQueueEditControllerEntryTest<Login> {

	@Override
	@Autowired
	@Qualifier("loginType")
	public void setModelTaskQueueType(String modelTaskQueueType) {
		super.setModelTaskQueueType(modelTaskQueueType);
	}

	@Override
	@Autowired
	@Qualifier("loginRegistry")
	public void setGetterSetter(GetterSetter<Login> getter) {
		super.setGetterSetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("loginTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<Login> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginScheduleDeleteTask")
	public void setDeleteTask(ScheduleDeleteTask<Login> deleteTask) {
		super.setDeleteTask(deleteTask);
	}

	@Override
	@Autowired
	@Qualifier("loginScheduleCreateReview")
	public void setScheduleCreateReviewTask(ScheduleCreateReviewTask<Login> scheduleCreateReviewTask) {
		super.setScheduleCreateReviewTask(scheduleCreateReviewTask);
	}

	@Override
	@Autowired
	@Qualifier("loginScheduleUpdateReview")
	public void setScheduleUpdateReviewTask(ScheduleUpdateReviewTask<Login> scheduleUpdateReviewTask) {
		super.setScheduleUpdateReviewTask(scheduleUpdateReviewTask);
	}

}
