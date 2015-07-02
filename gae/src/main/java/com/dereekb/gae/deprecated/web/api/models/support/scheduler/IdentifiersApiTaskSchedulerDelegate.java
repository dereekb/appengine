package com.thevisitcompany.gae.deprecated.web.api.models.support.scheduler;

import java.util.List;

import com.thevisitcompany.gae.server.taskqueue.TaskQueueParamPair;
import com.thevisitcompany.gae.server.taskqueue.TaskQueuePushRequest;

/**
 * Extension of {@link StaticApiTaskSchedulerDelegate} that can also append
 * identifiers passed through a {@link ApiTaskSchedulerRequest}.
 *
 * @author dereekb
 *
 */
public class IdentifiersApiTaskSchedulerDelegate extends StaticApiTaskSchedulerDelegate {

	private Boolean requireIdentifiers = true;
	private String identifiersParamName = "identifiers";

	@Override
	protected TaskQueuePushRequest createPushRequest(ApiTaskSchedulerRequest request) {
		TaskQueuePushRequest pushRequest = super.createPushRequest(request);
		List<Long> identifiers = request.getIdentifiers();

		if (identifiers != null && identifiers.isEmpty() == false) {
			TaskQueueParamPair pair = TaskQueueParamPair.getPairWithCommaSeparatedValues(this.identifiersParamName,
			        identifiers);
			pushRequest.addParameter(pair);
		} else if (this.requireIdentifiers) {
			throw new ApiTaskUnavailableException("Identifiers required for this request.");
		}

		return pushRequest;
	}

	public Boolean getRequireIdentifiers() {
		return this.requireIdentifiers;
	}

	public void setRequireIdentifiers(Boolean requireIdentifiers) {
		this.requireIdentifiers = requireIdentifiers;
	}

	public String getIdentifiersParamName() {
		return this.identifiersParamName;
	}

	public void setIdentifiersParamName(String identifiersParamName) {
		this.identifiersParamName = identifiersParamName;
	}

}
