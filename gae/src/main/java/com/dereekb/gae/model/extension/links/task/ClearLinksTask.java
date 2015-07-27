package com.dereekb.gae.model.extension.links.task;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Clears all relations from the input models. Generally used before deleting a
 * database model.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ClearLinksTask
        implements IterableTask<ModelKey> {

	private String type;
	private LinkSystem system;
	private boolean validateSave = true;

	public ClearLinksTask() {}

	public ClearLinksTask(String type, LinkSystem system) {
		this.type = type;
		this.system = system;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LinkSystem getSystem() {
		return this.system;
	}

	public void setSystem(LinkSystem system) {
		this.system = system;
	}

	public boolean isValidateSave() {
		return this.validateSave;
	}

	public void setValidateSave(boolean validateSave) {
		this.validateSave = validateSave;
	}

	// MARK: Task
	@Override
	public void doTask(Iterable<ModelKey> input) throws FailedTaskException {
		Set<ModelKey> keySet = IteratorUtility.iterableToSet(input);

		if (keySet.isEmpty() == false) {
			LinkModelSet modelSet = this.system.loadSet(this.type, keySet);
			List<LinkModel> models = modelSet.getModelsForKeys(keySet);
			this.clearRelationsForModels(models);
			modelSet.save(this.validateSave);
		}
	}

	private void clearRelationsForModels(List<LinkModel> models) {
		for (LinkModel model : models) {
			Collection<Link> links = model.getLinks();

			for (Link link : links) {
				link.clearRelations();
			}
		}
	}

	@Override
	public String toString() {
		return "ClearLinksTask [type=" + this.type + ", system=" + this.system + "]";
	}

}
