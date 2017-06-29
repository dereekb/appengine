package com.dereekb.gae.model.extension.links.task;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.impl.AbstractModelKeyListAccessorTask;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} that loads models referenced in a {@link ModelKeyListAccessor}
 * and clears the relationship links in a {@link LinkSystem}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ClearLinkTask<T extends UniqueModel> extends AbstractModelKeyListAccessorTask<T> {

	public static final boolean DEFAULT_VALIDATE = true;

	private Set<String> linkNames;
	private LinkSystem LinkSystem;

	private boolean validate = DEFAULT_VALIDATE;

	public ClearLinkTask(String linkName, LinkSystem LinkSystem) {
		this.setLinkName(linkName);
		this.setLinkSystem(LinkSystem);
	}

	public ClearLinkTask(Set<String> linkNames, LinkSystem LinkSystem) {
		this(linkNames, LinkSystem, DEFAULT_VALIDATE);
	}

	public ClearLinkTask(Set<String> linkNames, LinkSystem LinkSystem, boolean validate) {
		this.setLinkNames(linkNames);
		this.setLinkSystem(LinkSystem);
		this.setValidate(validate);
	}

	public Set<String> getLinkNames() {
		return this.linkNames;
	}

	public void setLinkName(String linkName) {
		this.setLinkNames(SetUtility.wrap(linkName));
	}

	public void setLinkNames(Set<String> linkNames) {
		if (linkNames == null) {
			throw new IllegalArgumentException("linkNames cannot be null.");
		}

		this.linkNames = linkNames;
	}

	public LinkSystem getLinkSystem() {
		return this.LinkSystem;
	}

	public void setLinkSystem(LinkSystem LinkSystem) {
		if (LinkSystem == null) {
			throw new IllegalArgumentException("LinkSystem cannot be null.");
		}

		this.LinkSystem = LinkSystem;
	}

	public boolean isValidate() {
		return this.validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	// MARK: Task
	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		String modelType = input.getModelType();
		List<ModelKey> keys = input.getModelKeys();

		LinkModelSet modelSet = this.LinkSystem.loadSet(modelType, keys);
		List<LinkModel> linkModels = modelSet.getModelsForKeys(keys);

		for (LinkModel model : linkModels) {
			for (String linkName : this.linkNames) {
				Link link = model.getLink(linkName);
				link.clearRelations();
			}
		}

		modelSet.save(this.validate);
	}

	@Override
	public String toString() {
		return "ClearLinkTask [linkNames=" + this.linkNames + ", LinkSystem=" + this.LinkSystem + ", validate="
		        + this.validate + "]";
	}
}
