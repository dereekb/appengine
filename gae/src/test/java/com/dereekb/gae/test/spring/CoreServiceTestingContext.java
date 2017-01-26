package com.dereekb.gae.test.spring;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.appengine.api.taskqueue.dev.LocalTaskQueueCallback;
import com.google.appengine.api.urlfetch.URLFetchServicePb.URLFetchRequest;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

/**
 * Testing context that initializes a Google App Engine
 * {@link LocalServiceTestHelper} from a Spring IoC container.
 *
 * @author dereekb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(name = "testing", locations = { CoreServiceTestingContext.GAE_TESTING_XML_PATH }) })
public class CoreServiceTestingContext {

	public static final String SRC_PATH = "file:src/";
	public static final String BASE_TESTING_PATH = SRC_PATH + "test/webapp/spring/";
	public static final String GAE_TESTING_XML_PATH = BASE_TESTING_PATH + "testing.xml";

	public static final String APPLICATION_TESTING_PATH = BASE_TESTING_PATH + "applications/";

	private Closeable session;

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	protected LocalTaskQueueTestConfig taskQueueTestConfig;

	@Autowired
	protected LocalServiceTestHelper helper;

	@Before
	public void setUpCoreServices() {
		this.taskQueueTestConfig.setTaskExecutionLatch(TestLocalTaskQueueCallback.countDownLatch);
		this.helper.setUp();
		this.session = ObjectifyService.begin();
	}

	@After
	public void tearDownCoreServices() {
		this.session.close();
		this.helper.tearDown();
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public LocalServiceTestHelper getHelper() {
		return this.helper;
	}

	public void setHelper(LocalServiceTestHelper helper) {
		this.helper = helper;
	}

	public static class TestLocalTaskQueueCallback
	        implements LocalTaskQueueCallback {

		private static final long serialVersionUID = 1L;

		private static String URL_PREFIX = "http://localhost:8080";
		private static boolean LOG_EVENTS = true;

		public static MockMvc mockMvc;

		public static final AtomicLong atomicCounter = new AtomicLong(0L);
		private static final LocalTaskQueueTestConfig.TaskCountDownLatch countDownLatch = new LocalTaskQueueTestConfig.TaskCountDownLatch(
		        0);

		public static final void safeWaitForLatch() throws InterruptedException {
			System.out.println("Waiting for latch....");

			waitForLatch();

			while (atomicCounter.get() != 0L) {
				System.out.println("Still waiting...");
				waitForLatch();
			}
		}

		private static final void waitForLatch() throws InterruptedException {
			Thread.sleep(200);
			countDownLatch.await();
		}

		private static final void increaseLatchCounter() {
			Long count = atomicCounter.incrementAndGet();

			System.out.println(String.format("Latch increasing to %s.", count));

			countDownLatch.reset(count.intValue());
		}

		private static final void decreaseLatchCounter() {
			Long count = atomicCounter.decrementAndGet();
			System.out.println(String.format("Latch decreased to %s.", count));
			countDownLatch.countDown();
		}

		@Override
		public int execute(URLFetchRequest arg0) {
			increaseLatchCounter();

			if (LOG_EVENTS) {
				System.out.println(String.format("Executing taskqueue task %s -> %s", arg0.getMethod(), arg0.getUrl()));
			}

			MockHttpServletRequestBuilder requestBuilder;

			try {
				requestBuilder = this.buildRequest(arg0);
			} catch (UnsupportedEncodingException e) {
				System.out.println(String.format("Exception decoding URL: %s.", arg0.getUrl()));
				e.printStackTrace();
				decreaseLatchCounter();
				return 0;
			}

			// Start Objectify service for the thread.
			Closeable ofy = ObjectifyService.begin();

			try {
				ResultActions resultActions = mockMvc.perform(requestBuilder);
				MockHttpServletResponse response = resultActions.andReturn().getResponse();
				int status = response.getStatus();
				// String error = response.getErrorMessage();
				return status;
			} catch (ServletException e) {
				e.printStackTrace();
				return 500;
			} catch (Exception e) {
				System.out.println(String.format("Exception occured while executing task %s.", arg0.getUrl()));
				e.printStackTrace();
				return 0;
			} finally {
				if (LOG_EVENTS) {
					System.out.println(String.format("Finished task at %s.", arg0.getUrl()));
				}

				ofy.close();
				decreaseLatchCounter();
			}
		}

		private MockHttpServletRequestBuilder buildRequest(URLFetchRequest arg0) throws UnsupportedEncodingException {
			HttpMethod httpMethod = null;

			switch (arg0.getMethod()) {
				case DELETE:
					httpMethod = HttpMethod.DELETE;
					break;
				case GET:
					httpMethod = HttpMethod.GET;
					break;
				case HEAD:
					httpMethod = HttpMethod.HEAD;
					break;
				case PATCH:
					httpMethod = HttpMethod.PATCH;
					break;
				case POST:
					httpMethod = HttpMethod.POST;
					break;
				case PUT:
					httpMethod = HttpMethod.PUT;
					break;
				default:
					break;
			}

			// Decode to prevent the mock request from encoding an encoded
			// value.
			String url = URLDecoder.decode(arg0.getUrl(), "UTF-8");
			url = url.replaceFirst(URL_PREFIX, "");

			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.request(httpMethod, url);
			return requestBuilder;
		}

		@Override
		public void initialize(Map<String, String> arg0) {}

		public static void waitUntilComplete() {
			try {
				safeWaitForLatch();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Stopped waiting for TaskQueue operation.");
		}

	}

}
