package com.dereekb.gae.test.applications.api.taskqueue.login.pointer;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleCreateReviewTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleUpdateReviewTask;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.deprecated.applications.api.taskqueue.tests.crud.TaskQueueEditControllerEntryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Disabled
@Deprecated
public class LoginPointerTaskQueueEditControllerEntryTest extends TaskQueueEditControllerEntryTest<LoginPointer> {

	@Override
	@Autowired
	@Qualifier("loginPointerType")
	public void setModelTaskQueueType(String modelTaskQueueType) {
		super.setModelTaskQueueType(modelTaskQueueType);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerRegistry")
	public void setGetterSetter(GetterSetter<LoginPointer> getter) {
		super.setGetterSetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<LoginPointer> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerScheduleDeleteTask")
	public void setDeleteTask(ScheduleDeleteTask<LoginPointer> deleteTask) {
		super.setDeleteTask(deleteTask);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerScheduleCreateReview")
	public void setScheduleCreateReviewTask(ScheduleCreateReviewTask<LoginPointer> scheduleCreateReviewTask) {
		super.setScheduleCreateReviewTask(scheduleCreateReviewTask);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerScheduleUpdateReview")
	public void setScheduleUpdateReviewTask(ScheduleUpdateReviewTask<LoginPointer> scheduleUpdateReviewTask) {
		super.setScheduleUpdateReviewTask(scheduleUpdateReviewTask);
	}

}
