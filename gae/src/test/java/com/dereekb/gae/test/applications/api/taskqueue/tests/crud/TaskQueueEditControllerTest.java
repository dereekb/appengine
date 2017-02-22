package com.dereekb.gae.test.applications.api.taskqueue.tests.crud;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.web.taskqueue.model.crud.TaskQueueEditController;


public class TaskQueueEditControllerTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("taskQueueEditController")
	private TaskQueueEditController editController;

	@Test
	public void testController() {
		Assert.assertNotNull(this.editController.getKeyTypeConverter());
		Assert.assertNotNull(this.editController.getEntries());
	}

}
