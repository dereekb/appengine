package com.dereekb.gae.web.api.server.schedule;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.WrappedApiUnprocessableEntityException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.server.schedule.exception.UnavailableSchedulerTaskException;
import com.dereekb.gae.web.api.server.schedule.impl.ApiScheduleTaskRequestImpl;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * REST controller that provides access to scheduling arbitrary taskqueue tasks.
 * <p>
 * Is administrator only, and tasks must be white-listed for use in
 * configuration.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/scheduler")
public class ApiScheduleTaskController {

	private TaskScheduler scheduler;
	private Map<String, ApiScheduleTaskControllerEntry> entries;

	public ApiScheduleTaskController(TaskScheduler scheduler, Map<String, ApiScheduleTaskControllerEntry> entries) {
		this.setScheduler(scheduler);
		this.setEntries(entries);
	}

	public TaskScheduler getScheduler() {
		return this.scheduler;
	}

	public void setScheduler(TaskScheduler scheduler) {
		if (scheduler == null) {
			throw new IllegalArgumentException("scheduler cannot be null.");
		}

		this.scheduler = scheduler;
	}

	public Map<String, ApiScheduleTaskControllerEntry> getEntries() {
		return this.entries;
	}

	public void setEntries(Map<String, ApiScheduleTaskControllerEntry> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("entries cannot be null.");
		}

		this.entries = new CaseInsensitiveMap<ApiScheduleTaskControllerEntry>(entries);
	}

	// MARK: Scheduling
	@ResponseBody
	@RequestMapping(value = "/schedule", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ApiResponse schedule(@Valid @RequestBody ApiScheduleTaskRequestImpl request)
	        throws UnavailableSchedulerTaskException {
		ApiResponse response = null;

		try {
			ApiScheduleTaskControllerEntry entry = this.getEntryForRequest(request);
			List<? extends TaskRequest> requests = entry.makeTaskRequests(request);
			this.scheduler.schedule(requests);
			response = new ApiResponseImpl();
		} catch (UnavailableSchedulerTaskException e) {
			throw new WrappedApiUnprocessableEntityException(e);
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	// MARK: Internal
	protected ApiScheduleTaskControllerEntry getEntryForRequest(ApiScheduleTaskRequest request)
	        throws UnavailableSchedulerTaskException {
		String taskName = request.getTask();
		ApiScheduleTaskControllerEntry entry = this.entries.get(taskName);

		if (entry == null) {
			throw new UnavailableSchedulerTaskException(taskName);
		}

		return entry;
	}

	@Override
	public String toString() {
		return "ApiScheduleTaskController [scheduler=" + this.scheduler + ", entries=" + this.entries + "]";
	}

}
