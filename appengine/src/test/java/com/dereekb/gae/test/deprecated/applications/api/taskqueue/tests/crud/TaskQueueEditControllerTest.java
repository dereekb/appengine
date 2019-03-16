package com.dereekb.gae.test.applications.api.taskqueue.tests.crud;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.web.taskqueue.model.crud.TaskQueueEditController;

/**
 *
 * @author dereekb
 *
 * @deprecated {@link TaskQueueEditController} is deprecated.
 */
@Disabled
@Deprecated
public class TaskQueueEditControllerTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("taskQueueEditController")
	private TaskQueueEditController editController;

	@Test
	public void testController() {
		assertNotNull(this.editController.getKeyTypeConverter());
		assertNotNull(this.editController.getEntries());
	}

}
