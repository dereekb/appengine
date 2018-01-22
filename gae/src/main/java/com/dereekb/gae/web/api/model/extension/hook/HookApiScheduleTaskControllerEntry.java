package com.dereekb.gae.web.api.model.extension.hook;

import java.util.Map;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.web.api.model.extension.iterate.ApiScheduleTaskRequest;
import com.dereekb.gae.web.api.model.extension.iterate.impl.AbstractSingleTaskApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;

public class HookApiScheduleTaskControllerEntry extends AbstractSingleTaskApiScheduleTaskControllerEntry {

	public static final String HOOK_EVENT_GROUP_PARAM = "hookEventGroup";
	public static final String HOOK_EVENT_CODE_PARAM = "hookEventCode";

	@Override
	public TaskRequest makeTaskRequest(ApiScheduleTaskRequest request)
	        throws MultiKeyedInvalidAttributeException,
	            KeyedInvalidAttributeException,
	            IllegalArgumentException {

		Map<String, String> params = request.getEncodedParameters();

		params.get(HOOK_EVENT_GROUP_PARAM);
		params.get(HOOK_EVENT_CODE_PARAM);


		// TODO Auto-generated method stub
		return null;
	}

}
