package com.thevisitcompany.gae.deprecated.web.api.models.base.objectify;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thevisitcompany.gae.deprecated.model.mod.search.query.ModelQueryRequest;
import com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.CrudsApiController;
import com.thevisitcompany.gae.deprecated.web.api.models.support.links.ApiLinksDirector;
import com.thevisitcompany.gae.deprecated.web.api.models.support.links.LinksChange;
import com.thevisitcompany.gae.deprecated.web.api.models.support.scheduler.ApiTaskScheduler;
import com.thevisitcompany.gae.deprecated.web.api.models.support.scheduler.ApiTaskSchedulerRequest;
import com.thevisitcompany.gae.deprecated.web.api.models.support.scheduler.ApiTaskUnavailableException;
import com.thevisitcompany.gae.deprecated.web.exceptions.ModelRequestException;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnsupportedRequestException;
import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;
import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;
import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModel;
import com.thevisitcompany.gae.server.taskqueue.TaskQueuePushRequest;

/**
 * Cruds API Controller used with Objectify Models
 *
 * @author dereekb
 *
 * @param <T>
 * @param <A>
 */
public abstract class ObjectifyApiController<T extends ObjectifyModel<T>, A> extends CrudsApiController<T, Long, A, ModelQueryRequest<T>> {

	private ApiTaskScheduler scheduler;
	private ApiLinksDirector<T, Long> linksDirector;

	@ResponseBody
	@PreAuthorize("hasPermission(this, 'schedule')")
	@RequestMapping(value = "/schedule", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ApiResponse schedule(@Valid @RequestBody ApiTaskSchedulerRequest request) throws ApiTaskUnavailableException {

		if (this.scheduler == null) {
			throw new UnsupportedRequestException("Scheduling is unsupported for this type.");
		}

		ApiResponse response = new ApiResponse();
		Collection<TaskQueuePushRequest> taskRequests = this.scheduler.schedule(request);
		response.putInfo("tasksCreated", taskRequests.size());
		response.setSuccess(true);
		return response;
	}

	@ResponseBody
	@PreAuthorize("hasPermission(this, 'link')")
	@RequestMapping(value = "/links/{action}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ApiResponse link(@PathVariable("action") String change,
	                        @RequestParam(required = false) Boolean force,
	                        @Valid @RequestBody LinksChange<Long> request) {

		if (this.linksDirector == null) {
			throw new UnsupportedRequestException("Links changes are unsupported for this type.");
		}

		LinksAction action = LinksAction.withString(change);

		if (action == null) {
			throw new ModelRequestException(null, "Invalid Links Type");
		}

		ApiResponse response = this.linksDirector.handleLinksChange(action, request, force);
		return response;
	}

	public ApiTaskScheduler getScheduler() {
		return this.scheduler;
	}

	public void setScheduler(ApiTaskScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public ApiLinksDirector<T, Long> getLinksDirector() {
		return this.linksDirector;
	}

	public void setLinksDirector(ApiLinksDirector<T, Long> linksDirector) {
		this.linksDirector = linksDirector;
	}

}
