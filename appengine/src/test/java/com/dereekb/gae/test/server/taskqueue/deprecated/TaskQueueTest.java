package com.dereekb.gae.test.server.taskqueue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.taskqueue.deprecated.TaskQueueParamPair;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueuePushRequest;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueuePushRequestHashFilter;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueueRequestBuilder;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Tests various helper functions related to the Task Queue.
 *
 * @author dereekb
 *
 */
@Deprecated
public class TaskQueueTest {

	@Test
	public void testRequestBuilderSplitTasks() {
		TaskQueuePushRequest request = new TaskQueuePushRequest("/test");
		request.setMethod(Method.POST);
		request.setCountdown(20L);

		String paramName = "first";
		String secondParamName = "second";

		TaskQueueParamPair pairA = new TaskQueueParamPair(paramName, "Value");
		TaskQueueParamPair pairB = new TaskQueueParamPair(secondParamName, "1,2,3,4,5,6");
		List<TaskQueueParamPair> pairs = new ArrayList<>();

		pairs.add(pairA);
		pairs.add(pairB);
		request.setParameters(pairs);

		TaskQueueRequestBuilder builder = new TaskQueueRequestBuilder();
		List<TaskQueuePushRequest> splitTasks = builder.splitTaskAtParameter(secondParamName, request);
		assertTrue(splitTasks.size() == 6);

		for (TaskQueuePushRequest task : splitTasks) {
			assertTrue(task.getMethod() == Method.POST);
			assertTrue(task.getCountdown() == 20L);
			assertTrue(task.getParameters().size() == 2);
		}
	}

	@Test
	public void testTaskQueueParamPairStaticFunctions() {

		List<Long> identifiers = new ArrayList<Long>();
		identifiers.add(1L);
		identifiers.add(2L);
		identifiers.add(3L);
		identifiers.add(4L);
		identifiers.add(5L);

		TaskQueueParamPair pair = TaskQueueParamPair.pairWithCommaSeparatedValue("identifiers", identifiers);
		assertTrue(pair.getValue().equals("1,2,3,4,5"));

		List<TaskQueueParamPair> pairs = TaskQueueParamPair.pairsForValues("identifiers", identifiers);
		assertTrue(pairs.size() == 5);
	}

	@Test
	public void testTaskQueueParamPairHashcodes() {

		TaskQueuePushRequest request = new TaskQueuePushRequest("/test");
		request.setMethod(Method.POST);
		request.setCountdown(1L);

		String paramName = "first";
		TaskQueueParamPair pair = new TaskQueueParamPair(paramName, "1,2,3");
		List<TaskQueueParamPair> pairs = new ArrayList<>();
		pairs.add(pair);
		request.setParameters(pairs);

		TaskQueueRequestBuilder builder = new TaskQueueRequestBuilder();
		List<TaskQueuePushRequest> splitTasks = builder.splitTaskAtParameter(paramName, request);
		assertTrue(splitTasks.size() == 3);

		TaskQueuePushRequest req1 = splitTasks.get(0);
		TaskQueuePushRequest req2 = splitTasks.get(1);
		assertTrue(req1.hashCode() != req2.hashCode());

		TaskQueuePushRequest req1Copy = req1.copy();
		TaskQueueParamPair req1CopyPair = new TaskQueueParamPair(paramName, "1");
		req1Copy.setParameters(SingleItem.withValue(req1CopyPair));

		assertTrue(req1.getRequestUrl().hashCode() == req1Copy.getRequestUrl().hashCode());

		// Collections may be different types, however. A SingleItem collection differs from a List collection's hashcode.
		assertFalse(req1.getParameters().hashCode() == req1Copy.getParameters().hashCode());

		// Use a separate function for retrieving the hashcode from pairs themselves.
		Integer req1Hash = req1.getParametersHashcode();
		Integer req1CopyHash = req1Copy.getParametersHashcode();
		assertTrue(req1Hash.equals(req1CopyHash));

		assertTrue(req1.hashCode() == req1Copy.hashCode());
	}

	@Test
	public void testTaskQueueHashFilterTest() {
		TaskQueuePushRequestHashFilter filter = new TaskQueuePushRequestHashFilter();

		TaskQueuePushRequest request = new TaskQueuePushRequest("/test");
		request.setMethod(Method.POST);
		request.setCountdown(60000000L);

		assertTrue(filter.filterObject(request) == FilterResult.PASS);

		// Attempting to use the request again should result in a fail.
		assertTrue(filter.filterObject(request) == FilterResult.FAIL);

		// Even with the countdown set to null, it should fail, since it has not elapsed yet.
		request.setCountdown(null);
		assertTrue(filter.filterObject(request) == FilterResult.FAIL);

		request.setRequestUrl("/newTest");
		request.setCountdown(0L);

		// No countdown means the request isnt added to the map
		assertTrue(filter.filterObject(request) == FilterResult.PASS);
		assertTrue(filter.filterObject(request) == FilterResult.PASS);
	}

	@Test
	public void testTaskQueueHashFilterConcurrencyTest() {
		final TaskQueuePushRequestHashFilter filter = new TaskQueuePushRequestHashFilter();
		final Runnable runnable = new Runnable() {

			@Override
            public void run() {
				for (int i = 0; i < 20; i += 1) {
					try {
						Thread.sleep(2);
					} catch (Exception ex) {
						TaskQueuePushRequest request = new TaskQueuePushRequest("/test");
						request.setMethod(Method.POST);
						assertTrue(filter.filterObject(request) == FilterResult.FAIL);
					}
				}
			}
		};

		// Set to be accurate down to 1 ms.
		filter.setAccuracy(1L);

		TaskQueuePushRequest request = new TaskQueuePushRequest("/test");
		request.setMethod(Method.POST);
		request.setCountdown(10L);
		assertTrue(filter.filterObject(request) == FilterResult.PASS);

		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);

		t1.run();
		t2.run();

		for (int i = 0; i < 20; i += 1) {
			TaskQueuePushRequest newRequest = new TaskQueuePushRequest("/test/" + i);
			newRequest.setMethod(Method.POST);
			newRequest.setCountdown(10000L);
			assertTrue(filter.filterObject(newRequest) == FilterResult.PASS);
		}

		try {
			Thread.sleep(200); // Wait for threads to finish.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue(filter.getCountdownMap().size() == (1 + 20));
	}

}
