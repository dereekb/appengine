package com.dereekb.gae.test.applications.api.taskqueue.tests.crud;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleCreateReviewTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleUpdateReviewTask;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskSchedulerImpl;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestConverter;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.utility.mock.TaskRequestMockHttpRequestConverter;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.web.taskqueue.model.crud.TaskQueueEditController;
import com.dereekb.gae.web.taskqueue.model.crud.TaskQueueEditControllerEntry;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Abstract test for a {@link TaskQueueEditControllerEntry}.
 * <p>
 * Executes the review then checks with the implementation to see if all
 * expected changes occurred.
 *
 * TODO: Create new unit tests.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 *
 * @deprecated {@link TaskQueueEditController} is deprecated.
 */
@Disabled
@Deprecated
public abstract class TaskQueueEditControllerEntryTest<T extends UniqueModel> extends ApiApplicationTestContext {

	private Integer genCount = 2;

	private String modelTaskQueueType;

	private GetterSetter<T> getterSetter;
	private TestModelGenerator<T> modelGenerator;
	private ScheduleDeleteTask<T> deleteTask;

	private TaskSchedulerImpl taskSchedulerImpl;

	private ScheduleCreateReviewTask<T> scheduleCreateReviewTask;
	private ScheduleUpdateReviewTask<T> scheduleUpdateReviewTask;

	private TaskRequestMockHttpRequestConverter mockRequestConverter;

	@Autowired
	@Qualifier("taskQueueEditController")
	private TaskQueueEditController controller;

	public Integer getGenCount() {
		return this.genCount;
	}

	public void setGenCount(Integer genCount) {
		this.genCount = genCount;
	}

	public String getModelTaskQueueType() {
		return this.modelTaskQueueType;
	}

	public void setModelTaskQueueType(String modelTaskQueueType) {
		this.modelTaskQueueType = modelTaskQueueType;
	}

	public GetterSetter<T> getGetterSetter() {
		return this.getterSetter;
	}

	public void setGetterSetter(GetterSetter<T> getterSetter) {
		this.getterSetter = getterSetter;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	public ScheduleDeleteTask<T> getDeleteTask() {
		return this.deleteTask;
	}

	public void setDeleteTask(ScheduleDeleteTask<T> deleteTask) {
		this.deleteTask = deleteTask;
	}

	public TaskSchedulerImpl getTaskSchedulerImpl() {
		return this.taskSchedulerImpl;
	}

	@Autowired
	public void setTaskSchedulerImpl(TaskSchedulerImpl taskSchedulerImpl) {
		this.taskSchedulerImpl = taskSchedulerImpl;

		TaskRequestConverter taskRequestConverter = taskSchedulerImpl.getConverter();
		this.mockRequestConverter = new TaskRequestMockHttpRequestConverter(taskRequestConverter,
		        this.serviceRequestBuilder);
	}

	public ScheduleCreateReviewTask<T> getScheduleCreateReviewTask() {
		return this.scheduleCreateReviewTask;
	}

	public void setScheduleCreateReviewTask(ScheduleCreateReviewTask<T> scheduleCreateReviewTask) {
		this.scheduleCreateReviewTask = scheduleCreateReviewTask;
	}

	public ScheduleUpdateReviewTask<T> getScheduleUpdateReviewTask() {
		return this.scheduleUpdateReviewTask;
	}

	public void setScheduleUpdateReviewTask(ScheduleUpdateReviewTask<T> scheduleUpdateReviewTask) {
		this.scheduleUpdateReviewTask = scheduleUpdateReviewTask;
	}

	public TaskQueueEditController getController() {
		return this.controller;
	}

	public void setController(TaskQueueEditController controller) {
		this.controller = controller;
	}

	// Make
	public List<T> create(boolean related) {
		List<T> models = this.modelGenerator.generate(this.genCount);

		for (T model : models) {
			if (related) {
				this.createRelated(model);
			} else {
				this.removeRelated(model, this.getterSetter);
			}
		}

		return models;
	}

	/**
	 * Removes all related components from the model, then saves it.
	 *
	 * @param model
	 * @param setter
	 */
	protected void removeRelated(T model,
	                             Setter<T> setter) {}

	/**
	 * Creates all related models and links them together.
	 *
	 * @param model
	 */
	protected void createRelated(T model) {
		// Nothing to create by default.
	}

	// Tests
	@Test
	public void testCreateFunction() {
		List<T> models = this.create(false);
		List<String> keys = ModelKey.readStringKeys(models);

		this.controller.reviewCreate(this.modelTaskQueueType, keys);

		models = this.getterSetter.get(models);

		assertTrue(this.isProperlyInitialized(models));
	}

	protected final boolean isProperlyInitialized(List<T> models) {
		boolean success = true;

		for (T model : models) {
			if (success == false) {
				break;
			}

			success = this.isProperlyInitialized(model);
		}

		return success;
	}

	protected boolean isProperlyInitialized(T model) {
		return true;
	}

	// MARK: Directly
	@Test
	public void testEditFunction() {
		List<T> models = this.create(true);
		List<String> keys = ModelKey.readStringKeys(models);

		// Complete Initializing Models
		this.controller.reviewCreate(this.modelTaskQueueType, keys);

		models = this.getterSetter.get(models);

		assertTrue(this.isProperlyInitialized(models));

		this.controller.reviewUpdate(this.modelTaskQueueType, keys);
	}

	@Test
	public final void testDeleteFunction() {
		List<T> models = this.create(true);
		this.testDelete(models);
	}

	public void testDelete(List<T> models) {
		List<String> keys = ModelKey.readStringKeys(models);

		// Complete Initializing Models
		this.controller.reviewCreate(this.modelTaskQueueType, keys);

		models = this.getterSetter.get(models);

		assertTrue(this.isProperlyInitialized(models));

		this.controller.processDelete(this.modelTaskQueueType, keys);

		assertTrue(this.isProperlyDeleted(models));
	}

	@Test
	public void testDeleteScheduling() {
		List<T> models = this.create(true);
		List<MutableTaskRequest> requests = this.deleteTask.getBuilder().buildRequests(models);

		assertNotNull(requests);

		for (TaskRequest request : requests) {
			assertNotNull(request.getPath());
			assertTrue(request.getMethod() == Method.DELETE);

			Collection<KeyedEncodedParameter> parameters = request.getParameters();
			assertFalse(parameters.isEmpty());
		}

	}

	protected final boolean isProperlyDeleted(List<T> models) {
		boolean success = (this.getterSetter.allExist(ModelKey.readModelKeys(models)) == false);

		for (T model : models) {
			if (success == false) {
				break;
			}

			success = this.isProperlyDeleted(model);
		}

		return success;
	}

	/**
	 * Checks the model's values to see whether or not it has been properly
	 * deleted.
	 * <p>
	 * Override.
	 */
	protected boolean isProperlyDeleted(T model) {
		return true;
	}

	// MARK: API Test
	@Test
	@Disabled
	public void testTaskQueueApiCreateReview() throws Exception {

		// NOTE: Not really a necessary test.

		List<T> models = this.create(false);
		this.scheduleCreateReviewTask.sendTasks(models);

		List<? extends TaskRequest> requests = this.scheduleCreateReviewTask.getBuilder().buildRequests(models);

		this.taskSchedulerImpl.schedule(requests);

		this.waitForTaskQueueToComplete();

		List<MockHttpServletRequestBuilder> mockRequests = this.mockRequestConverter.convert(requests);
		this.performHttpRequests(mockRequests);
	}

}
