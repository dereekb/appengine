package com.dereekb.gae.web.taskqueue.model.extension.iterate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.utilities.collections.map.impl.CaseInsensitiveEntryContainer;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.exception.UnregisteredIterateTypeException;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.impl.IterateTaskInputImpl;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Task Queue controller used for performing custom tasks over input models.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/taskqueue")
public class TaskQueueIterateController extends CaseInsensitiveEntryContainer<TaskQueueIterateControllerEntry>{

	private static final Logger LOGGER = Logger.getLogger(TaskQueueIterateController.class.getName());

	public static final String ITERATE_PATH = "iterate";

	public static final String SEQUENCE_PATH = "sequence";

	/**
	 * HTTP header for the step value.
	 *
	 * @see #iterate(String, Integer, String, String, Map)
	 */
	public static final String TASK_STEP_HEADER = "TQ_ITERATE_STEP";

	/**
	 * HTTP header for the cursor value.
	 *
	 * @see #iterate(String, Integer, String, String, Map)
	 */
	public static final String CURSOR_HEADER = "TQ_CURSOR";

	private TaskScheduler scheduler;
	private TypeModelKeyConverter keyTypeConverter;

	public TaskQueueIterateController() {}

	public TaskQueueIterateController(TaskScheduler scheduler,
	        TypeModelKeyConverter keyTypeConverter,
	        Map<String, TaskQueueIterateControllerEntry> entries) throws IllegalArgumentException {
		super(entries);
		this.setScheduler(scheduler);
		this.setKeyTypeConverter(keyTypeConverter);
	}

	public TaskScheduler getScheduler() {
		return this.scheduler;
	}

	public void setScheduler(TaskScheduler scheduler) throws IllegalArgumentException {
		if (scheduler == null) {
			throw new IllegalArgumentException("Scheduler cannot be null.");
		}

		this.scheduler = scheduler;
	}

	public TypeModelKeyConverter getKeyTypeConverter() {
		return this.keyTypeConverter;
	}

	public void setKeyTypeConverter(TypeModelKeyConverter keyTypeConverter) {
		if (keyTypeConverter == null) {
			throw new IllegalArgumentException("keyTypeConverter cannot be null.");
		}

		this.keyTypeConverter = keyTypeConverter;
	}

	/**
	 * Performs an iteration task using a
	 * {@link TaskQueueIterateControllerEntry} that corresponds to the input
	 * {@code modelType} value. The {@code step} and {@code cursorKey} values
	 * are for the Task Queue.
	 *
	 * @param modelType
	 *            The model type. Never {@code null}.
	 * @param step
	 *            The current iteration step, or {@code null} if it is the first
	 *            step.
	 * @param cursorKey
	 *            The cursor key, or {@code null} if the request has not yet
	 *            been started.
	 * @param taskName
	 *            The task name to use. Never {@code null}.
	 * @param parameters
	 *            All request parameters. Never {@code null}.
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{type}/iterate/{task}", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void iterate(@PathVariable("type") String modelType,
	                    @PathVariable("task") String taskName,
	                    @RequestHeader(value = TASK_STEP_HEADER, required = false) Integer step,
	                    @RequestHeader(value = CURSOR_HEADER, required = false) String cursor,
	                    @RequestParam Map<String, String> parameters) {

		try {
			TaskQueueIterateControllerEntry entry = this.getEntryForType(modelType);
			IterateTaskInputImpl input = new IterateTaskInputImpl(taskName, modelType, cursor, step, parameters);
			IterateTaskRequestImpl request = new IterateTaskRequestImpl(input);
			entry.performIterateTask(request);
		} catch (RuntimeException e) {
			LOGGER.log(Level.SEVERE, "Iterate task failed.", e);
			throw e;
		}
	}

	/**
	 * Performs an sequence task using a
	 * {@link TaskQueueIterateControllerEntry} that corresponds to the input
	 * {@code modelType} value.
	 *
	 * @param modelType
	 *            The model type. Never {@code null}.
	 * @param taskName
	 *            The task name to use. Never {@code null}.
	 * @param identifiers
	 *            Keys in the sequence.
	 * @param parameters
	 *            All request parameters. Never {@code null}.
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{type}/sequence/{task}", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void sequence(@PathVariable("type") String modelType,
	                     @PathVariable("task") String taskName,
	                     @RequestParam("keys") List<String> identifiers,
	                     @RequestParam Map<String, String> parameters) {

		try {
			TaskQueueIterateControllerEntry entry = this.getEntryForType(modelType);
			IterateTaskInputImpl input = new IterateTaskInputImpl(taskName, modelType, null, 0, parameters);

			List<ModelKey> sequence = this.keyTypeConverter.convertKeys(modelType, identifiers);

			SequenceTaskRequestImpl request = new SequenceTaskRequestImpl(input, sequence);
			entry.performSequenceTask(request);
		} catch (RuntimeException e) {
			LOGGER.log(Level.SEVERE, "Sequence task failed.", e);
			throw e;
		}
	}

	public static String pathForIterateTask(String modelType,
	                                        String taskName) {
		return String.format("%s/iterate/%s", modelType, taskName);
	}

	public static String pathForSequenceTask(String modelType,
	                                         String taskName) {
		return String.format("%s/sequence/%s", modelType, taskName);
	}

	// MARK: Entries
	@Override
	protected void throwEntryDoesntExistException(String type) throws RuntimeException {
		throw new UnregisteredIterateTypeException("Unknown type: " + type);
	}

	@Override
	public String toString() {
		return "TaskQueueIterateController [entries=" + this.getEntries() + "]";
	}

	private class IterateTaskRequestImpl extends AbstractTaskRequest
	        implements IterateTaskRequest {

		public IterateTaskRequestImpl(IterateTaskInputImpl taskInput) {
			super(taskInput);
		}

		@Override
		public void scheduleContinuation(String cursor) {
			String path = this.getContinutationPath();
			Collection<KeyedEncodedParameter> headers = this.getContinuationHeaders(cursor);
			Collection<KeyedEncodedParameter> parameters = this.getContinuationParameters();

			TaskRequestImpl request = new TaskRequestImpl(path, Method.PUT);
			request.setHeaders(headers);
			request.setParameters(parameters);

			TaskQueueIterateController.this.scheduler.schedule(request);
		}

		private String getContinutationPath() {
			String taskName = this.taskInput.getTaskName();
			String modelType = this.taskInput.getModelType();
			return pathForIterateTask(modelType, taskName);
		}

		private Collection<KeyedEncodedParameter> getContinuationHeaders(String cursor) {
			Integer step = this.taskInput.getIterationStep() + 1;

			List<KeyedEncodedParameter> parameters = new ArrayList<KeyedEncodedParameter>();
			parameters.add(new KeyedEncodedParameterImpl(TASK_STEP_HEADER, step));
			parameters.add(new KeyedEncodedParameterImpl(CURSOR_HEADER, cursor));

			return parameters;
		}

		private Collection<KeyedEncodedParameter> getContinuationParameters() {
			Map<String, String> parameters = this.taskInput.getParameters();
			List<KeyedEncodedParameterImpl> impl = KeyedEncodedParameterImpl.makeParametersWithMap(parameters);
			return new ArrayList<KeyedEncodedParameter>(impl);
		}

	}

	private class SequenceTaskRequestImpl extends AbstractTaskRequest
	        implements SequenceTaskRequest {

		private final List<ModelKey> sequence;

		public SequenceTaskRequestImpl(IterateTaskInputImpl taskInput, List<ModelKey> sequence) {
			super(taskInput);
			this.sequence = sequence;
		}

		@Override
		public List<ModelKey> getSequence() {
			return this.sequence;
		}

	}

	private abstract class AbstractTaskRequest {

		protected final IterateTaskInput taskInput;

		public AbstractTaskRequest(IterateTaskInputImpl taskInput) {
			this.taskInput = taskInput;
		}

		public IterateTaskInput getTaskInput() {
			return this.taskInput;
		}
	}

}
