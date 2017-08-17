package com.dereekb.gae.model.crud.services.components.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.model.crud.task.UpdateTask;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyTransactionUtility;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.googlecode.objectify.Work;

/**
 * {@link UpdateServiceImpl} extension that performs the update within an Google
 * App Engine transaction, ensuring the model being updated is always the latest version.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class SafeUpdateServiceImpl<T extends UniqueModel> extends UpdateServiceImpl<T> {

	private TaskRequestSender<T> reviewTaskSender;

	public SafeUpdateServiceImpl(ReadService<T> readService, UpdateTask<T> updateTask) throws IllegalArgumentException {
		super(readService, updateTask);
	}

	public SafeUpdateServiceImpl(ReadService<T> readService,
	        UpdateTask<T> updateTask,
	        TaskRequestSender<T> reviewTaskSender) throws IllegalArgumentException {
		super(readService, updateTask);
		this.setReviewTaskSender(reviewTaskSender);
	}

	public TaskRequestSender<T> getReviewTaskSender() {
		return this.reviewTaskSender;
	}

	public void setReviewTaskSender(TaskRequestSender<T> reviewTaskSender) {
		this.reviewTaskSender = reviewTaskSender;
	}

	// MARK: UpdateService
	@Override
	public UpdateResponse<T> update(final UpdateRequest<T> request)
	        throws AtomicOperationException,
	            IllegalArgumentException {

		Work<UpdateResponse<T>> work = new Work<UpdateResponse<T>>() {

			@Override
			public UpdateResponse<T> run() {
				return SafeUpdateServiceImpl.super.update(request);
			}

		};

		UpdateResponse<T> response = ObjectifyTransactionUtility.doTransactNew(work);

		this.reviewWithResponse(response);

		return response;
	}

	protected void reviewWithResponse(UpdateResponse<T> response) {
		Collection<T> models = response.getModels();

		// Update
		if (this.reviewTaskSender != null) {
			this.reviewTaskSender.sendTasks(models);
		}
	}

	@Override
	public String toString() {
		return "UpdateServiceImpl [getReadService()=" + this.getReadService() + ", getUpdateTask()="
		        + this.getUpdateTask() + "]";
	}

}