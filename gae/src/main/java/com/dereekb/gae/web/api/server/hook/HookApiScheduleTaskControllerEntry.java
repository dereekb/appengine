package com.dereekb.gae.web.api.server.hook;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;
import com.dereekb.gae.web.api.server.schedule.impl.AbstractSingleTaskApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;
import com.fasterxml.jackson.databind.JsonNode;

public class HookApiScheduleTaskControllerEntry extends AbstractSingleTaskApiScheduleTaskControllerEntry {

	public static final String ENTRY_KEY = "webhook";

	@Override
	public TaskRequest makeTaskRequest(ApiScheduleTaskRequest request)
	        throws MultiKeyedInvalidAttributeException,
	            KeyedInvalidAttributeException,
	            IllegalArgumentException {

		JsonNode data = request.getData();

		// TODO Auto-generated method stub
		return null;
	}

}
