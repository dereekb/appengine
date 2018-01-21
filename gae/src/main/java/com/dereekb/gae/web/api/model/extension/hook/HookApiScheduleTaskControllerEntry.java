package com.dereekb.gae.web.api.model.extension.hook;

import java.util.Map;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.web.api.model.extension.iterate.ApiScheduleTaskRequest;
import com.dereekb.gae.web.api.model.extension.iterate.impl.AbstractSingleTaskApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;

public class HookApiScheduleTaskControllerEntry extends AbstractSingleTaskApiScheduleTaskControllerEntry {

	@Override
	public TaskRequest makeTaskRequest(ApiScheduleTaskRequest request)
	        throws MultiKeyedInvalidAttributeException,
	            KeyedInvalidAttributeException,
	            IllegalArgumentException {

		Map<String, String> params = request.getEncodedParameters();



		// TODO Auto-generated method stub
		return null;
	}

}
