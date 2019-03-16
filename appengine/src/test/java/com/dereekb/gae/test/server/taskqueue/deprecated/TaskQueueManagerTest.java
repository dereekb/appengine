package com.dereekb.gae.test.server.taskqueue;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.taskqueue.deprecated.TaskQueueManager;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueueParamPair;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueuePushRequest;
import com.google.appengine.api.taskqueue.TaskOptions;

@Deprecated
public class TaskQueueManagerTest {

	@Test
	public void testBuildingManager() {
		TaskQueueManager manager = new TaskQueueManager();
		manager.setQueueName("queueName");
		manager.setDefaultCountdown(0L);
		manager.setBaseUrl("/base/taskqueue/url");
	}

	@Test
	public void testStaticRequestBuilder() {

		String[] valuesArray = new String[] { "a", "b", "c" };
		Iterable<String> values = Arrays.asList(valuesArray);
		String expectedValue = "a,b,c";
		String testParam = "letters";

		TaskQueueParamPair pair = TaskQueueParamPair.pairWithCommaSeparatedValue(testParam, values);

		String parameter = pair.getParameter();
		String value = pair.getValue();
		assertTrue(expectedValue.equals(value));
		assertTrue(parameter.equals(testParam));
	}

	@Test
	public void testBuildingPushRequest() {

		String requestUrl = "Request URL";
		TaskQueuePushRequest request = new TaskQueuePushRequest(requestUrl);
		assertNotNull(request.getRequestUrl());
		assertTrue(requestUrl.equals(request.getRequestUrl()));

	}

	@Test
	public void testQueueOptionsBuilding() {
		TaskQueueManager manager = new TaskQueueManager();

		TaskQueuePushRequest requestA = new TaskQueuePushRequest("No parameters");
		TaskOptions optionA = manager.buildTaskOption(requestA);
		assertNotNull(optionA);

		Long testEta = 10000L;
		TaskQueuePushRequest requestB = new TaskQueuePushRequest("With ETA");
		requestB.setEta(testEta);

		TaskOptions optionB = manager.buildTaskOption(requestB);
		assertNotNull(optionB);
		assertNotNull(optionB.getEtaMillis());
		assertTrue(testEta.equals(optionB.getEtaMillis()));

	}

}
