package com.dereekb.gae.test.applications.api.server;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.dereekb.gae.client.api.server.schedule.impl.ClientApiScheduleTaskRequestImpl;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.model.shared.event.impl.CommonModelEventType;
import com.dereekb.gae.server.event.webhook.impl.WebHookEventImpl;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.web.api.server.hook.HookApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.server.schedule.impl.ApiScheduleTaskRequestImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Ignore
public class HookApiScheduleTaskControllerTest extends ApiApplicationTestContext {

	// MARK: Client Tests
	@Test
	public void testSerializingClientApiScheduleTaskRequestImpl() throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		EventType eventType = CommonModelEventType.CREATED;
		List<String> testData = ListUtility.toList("a", "b", "c");
		WebHookEventImpl event = new WebHookEventImpl("scope", eventType, testData);

		JsonNode node = event.getJsonNode();
		String json = mapper.writeValueAsString(node);

		ClientApiScheduleTaskRequestImpl test = new ClientApiScheduleTaskRequestImpl();

		test.setTask(HookApiScheduleTaskControllerEntry.ENTRY_KEY);
		test.setRawData(json);

		String requestJson = mapper.writeValueAsString(test);

		ApiScheduleTaskRequestImpl request = mapper.readerFor(ApiScheduleTaskRequestImpl.class).readValue(requestJson);
		String requestTask = request.getTask();

		Assert.assertTrue(requestTask.equalsIgnoreCase(test.getTask()));
		Assert.assertTrue(request.getData() != null);

	}

}
