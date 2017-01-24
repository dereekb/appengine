package com.dereekb.gae.model.extension.links.task;

import java.util.List;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.TaskQueueIterateTaskFactory;

/**
 * {@link TaskQueueIterateTaskFactory} implementation that facilitates link
 * changes.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LinkModelChangeTaskFactory<T extends UniqueModel>
        implements TaskQueueIterateTaskFactory<T> {

	private LinkSystem system;
	private LinkModelChangeTaskFactoryDelegate delegate;

	private boolean validate;

	public LinkModelChangeTaskFactory(LinkSystem system, LinkModelChangeTaskFactoryDelegate delegate) {
		super();
		this.setSystem(system);
		this.setDelegate(delegate);
	}

	public LinkModelChangeTaskFactoryDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(LinkModelChangeTaskFactoryDelegate delegate) throws IllegalArgumentException {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	public LinkSystem getSystem() {
		return this.system;
	}

	public void setSystem(LinkSystem system) throws IllegalArgumentException {
		if (system == null) {
			throw new IllegalArgumentException("LinkSystem cannot be null.");
		}

		this.system = system;
	}

	public boolean isValidate() {
		return this.validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	// MARK: TaskQueueIterateTaskFactory
	@Override
	public Task<ModelKeyListAccessor<T>> makeTask(IterateTaskInput input) throws FactoryMakeFailureException {
		ModelLinkChangeTaskDelegate taskDelegate = this.delegate.makeTaskDelegate(input);
		return new LinkModelChangeTask(input.getModelType(), taskDelegate);
	}

	/**
	 * {@link LinkModelChangeTask} delegate.
	 * 
	 * @author dereekb
	 *
	 */
	public interface ModelLinkChangeTaskDelegate {

		/**
		 * Changes the input {@link LinkModel}.
		 * 
		 * @param model
		 *            {@link LinkModel}. Never {@code null}.
		 * @throws FailedTaskException
		 *             thrown if the link change cannot be done.
		 */
		public void modifyLinkModel(LinkModel model) throws FailedTaskException;

	}

	/**
	 * {@link Task} generated by {@link LinkModelChangeTaskFactory}.
	 * 
	 * @author dereekb
	 *
	 */
	public class LinkModelChangeTask
	        implements Task<ModelKeyListAccessor<T>> {

		private final String modelType;
		private final ModelLinkChangeTaskDelegate delegate;

		public LinkModelChangeTask(String modelType, ModelLinkChangeTaskDelegate delegate) {
			super();
			this.modelType = modelType;
			this.delegate = delegate;
		}

		// MARK: Task
		@Override
		public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
			List<ModelKey> keys = input.getModelKeys();

			LinkModelSet modelSet = LinkModelChangeTaskFactory.this.system.loadSet(this.modelType, keys);
			List<LinkModel> linkModels = modelSet.getModelsForKeys(keys);

			try {
				for (LinkModel model : linkModels) {
					this.delegate.modifyLinkModel(model);
				}
			} catch (FailedTaskException e) {
				throw e;
			} catch (RuntimeException e) {
				throw new FailedTaskException(e);
			}

			modelSet.save(LinkModelChangeTaskFactory.this.validate);
		}

		@Override
		public String toString() {
			return "LinkModelChangeTask [modelType=" + this.modelType + ", delegate=" + this.delegate + "]";
		}

	}

	@Override
	public String toString() {
		return "LinkModelChangeTaskFactory [system=" + this.system + ", delegate=" + this.delegate + ", validate="
		        + this.validate + "]";
	}

}
