package com.dereekb.gae.model.extension.links.crud.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.task.CreateTask;
import com.dereekb.gae.model.crud.task.config.CreateTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.CreateTaskConfigImpl;
import com.dereekb.gae.model.extension.links.exception.ApiLinkException;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.model.extension.links.service.LinkServiceResponse;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangeException;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangeSetException;
import com.dereekb.gae.model.extension.links.service.impl.LinkServiceRequestImpl;
import com.dereekb.gae.server.datastore.Deleter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.map.MapUtility;
import com.dereekb.gae.utilities.collections.pairs.ResultsPairState;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.api.util.attribute.impl.InvalidAttributeImpl;

/**
 * {@link CreateTask} implementation that also uses the {@link LinkService} to
 * change links before completing the task.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LinkCreateTaskImpl<T extends UniqueModel>
        implements CreateTask<T> {

	private CreateTask<T> createTask;
	private Deleter<T> undoDeleter;

	private LinkService linkService;
	private LinkCreateTaskDelegate<T> delegate;

	private TaskRequestSender<T> reviewTaskSender;

	public LinkCreateTaskImpl(CreateTask<T> createTask,
	        Deleter<T> undoDeleter,
	        LinkCreateTaskDelegate<T> delegate,
	        LinkService linkService,
	        TaskRequestSender<T> reviewTaskSender) {
		this.setCreateTask(createTask);
		this.setUndoDeleter(undoDeleter);
		this.setDelegate(delegate);
		this.setLinkService(linkService);
		this.setReviewTaskSender(reviewTaskSender);
	}

	public CreateTask<T> getCreateTask() {
		return this.createTask;
	}

	public void setCreateTask(CreateTask<T> createTask) {
		if (createTask == null) {
			throw new IllegalArgumentException("createTask cannot be null.");
		}

		this.createTask = createTask;
	}

	public Deleter<T> getUndoDeleter() {
		return this.undoDeleter;
	}

	public void setUndoDeleter(Deleter<T> undoDeleter) {
		if (undoDeleter == null) {
			throw new IllegalArgumentException("undoDeleter cannot be null.");
		}

		this.undoDeleter = undoDeleter;
	}

	public LinkService getLinkService() {
		return this.linkService;
	}

	public void setLinkService(LinkService linkService) {
		if (linkService == null) {
			throw new IllegalArgumentException("linkService cannot be null.");
		}

		this.linkService = linkService;
	}

	public LinkCreateTaskDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(LinkCreateTaskDelegate<T> delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	public TaskRequestSender<T> getReviewTaskSender() {
		return this.reviewTaskSender;
	}

	public void setReviewTaskSender(TaskRequestSender<T> reviewTaskSender) {
		if (reviewTaskSender == null) {
			throw new IllegalArgumentException("reviewTaskSender cannot be null.");
		}

		this.reviewTaskSender = reviewTaskSender;
	}

	// MARK: CreateTask
	@Override
	public void doTask(Iterable<CreatePair<T>> input) throws FailedTaskException, AtomicOperationException {
		this.doTask(input, new CreateTaskConfigImpl(true));
	}

	@Override
	public void doTask(Iterable<CreatePair<T>> input,
	                   CreateTaskConfig configuration)
	        throws AtomicOperationException {

		// Create Normally. Items should return with an identifier.
		this.createTask.doTask(input, configuration);

		// Update Links
		List<CreatePair<T>> pairs = CreatePair.pairsWithState(input, ResultsPairState.SUCCESS);
		List<T> created = CreatePair.getObjects(input);

		List<LinkCreateTaskPair<T>> linkPairs = this.delegate.buildTaskPairs(pairs);

		try {
			this.performLinkChanges(linkPairs, configuration);

			// Send the review task
			if (this.reviewTaskSender != null) {
				this.reviewTaskSender.sendTasks(created);
			}
		} catch (AtomicOperationException e) {
			// Failure if any of the created/primary models are missing.
			// Generally this shouldn't occur, but caught just in-case.
			this.undoDeleter.delete(created);
			throw e;
		} catch (LinkSystemChangeSetException e) {

			// If the atomic operation fails, delete all created items to undo.
			this.undoDeleter.delete(created);

			this.setPairFailuresForChangeSetException(linkPairs, e);

			throw new AtomicOperationException(e);
		}
	}

	private void setPairFailuresForChangeSetException(List<LinkCreateTaskPair<T>> pairs,
	                                                  LinkSystemChangeSetException e) {

		List<LinkSystemChangeException> exceptions = e.getExceptions();

		Map<LinkSystemChange, LinkCreateTaskPair<T>> pairsMap = new HashMap<LinkSystemChange, LinkCreateTaskPair<T>>();

		for (LinkCreateTaskPair<T> pair : pairs) {
			MapUtility.putIntoMapMultipleTimes(pairsMap, pair.getObject(), pair);
		}

		for (LinkSystemChangeException exception : exceptions) {
			LinkSystemChange change = exception.getChange();

			LinkCreateTaskPair<T> pair = pairsMap.get(change);

			if (pair != null) {
				MapUtility.removeAll(pairsMap, pair.getObject());

				CreatePair<T> createPair = pair.getKey();

				InvalidAttributeImpl invalidAttribute = new InvalidAttributeImpl();

				ApiLinkException reason = exception.getReason();

				invalidAttribute.setAttribute(change.getLinkName());
				invalidAttribute.setValue(change.getTargetStringKeys().toString());
				invalidAttribute.setDetail("An error occured while trying to link models.");
				invalidAttribute.setErrorInfo(reason.asResponseError());

				createPair.setAttributeFailure(invalidAttribute);
			}
		}

	}

	private LinkServiceResponse performLinkChanges(List<LinkCreateTaskPair<T>> linkPairs,
	                                               CreateTaskConfig configuration) {
		List<List<LinkSystemChange>> pairChanges = LinkCreateTaskPair.getObjects(linkPairs);
		List<LinkSystemChange> changes = ListUtility.flatten(pairChanges);

		if (changes.isEmpty() == false) {
			LinkServiceRequestImpl request = new LinkServiceRequestImpl(changes, configuration.isAtomic());
			return this.linkService.updateLinks(request);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "LinkCreateTaskImpl [createTask=" + this.createTask + ", undoDeleter=" + this.undoDeleter
		        + ", linkService=" + this.linkService + ", delegate=" + this.delegate + ", reviewTaskSender="
		        + this.reviewTaskSender + "]";
	}

}
