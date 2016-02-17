package com.dereekb.gae.model.extension.links.task;

import java.util.List;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
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
public class ClearLinkTask<T extends UniqueModel>
        implements Task<ModelKeyListAccessor<T>> {

	private String linkName;
	private LinkSystem system;

	private boolean validate;

	public ClearLinkTask(String linkName, LinkSystem system) {
		this(linkName, system, true);
	}

	public ClearLinkTask(String linkName, LinkSystem system, boolean validate) {
		this.setLinkName(linkName);
		this.setSystem(system);
		this.setValidate(validate);
	}

	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public LinkSystem getSystem() {
		return this.system;
	}

	public void setSystem(LinkSystem system) {
		this.system = system;
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

		LinkModelSet modelSet = this.system.loadSet(modelType, keys);
		List<LinkModel> linkModels = modelSet.getModelsForKeys(keys);

		for (LinkModel model : linkModels) {
			Link link = model.getLink(this.linkName);
			link.clearRelations();
		}

		modelSet.save(this.validate);
	}

	@Override
	public String toString() {
		return "ClearLinkTask [linkName=" + this.linkName + ", system=" + this.system + ", validate=" + this.validate
		        + "]";
	}

}
