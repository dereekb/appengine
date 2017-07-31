package com.dereekb.gae.model.extension.links.task;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemInstanceOptionsImpl;
import com.dereekb.gae.model.extension.links.system.modification.impl.ReusableLinkModificationSystemRequestBuilder;
import com.dereekb.gae.model.extension.links.system.modification.impl.ReusableLinkModificationSystemRequestBuilder.RequestBuilderGroup;
import com.dereekb.gae.model.extension.links.system.modification.impl.ReusableLinkModificationSystemRequestBuilder.RequestBuilderItem;
import com.dereekb.gae.model.extension.links.system.modification.utility.LinkModificationSystemUtility;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.impl.AbstractModelKeyListAccessorTask;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} that loads models referenced in a {@link ModelKeyListAccessor}
 * and clears the relationship links using a LinkModificationSystem.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ClearLinkTask<T extends UniqueModel> extends AbstractModelKeyListAccessorTask<T> {

	public static final boolean DEFAULT_ATOMIC = false;

	private Set<String> linkNames;
	private LinkModificationSystem system;

	private boolean atomic = DEFAULT_ATOMIC;

	public ClearLinkTask(String linkName, LinkModificationSystem system) {
		this.setLinkName(linkName);
		this.setSystem(system);
	}

	public ClearLinkTask(Set<String> linkNames, LinkModificationSystem system) {
		this(linkNames, system, DEFAULT_ATOMIC);
	}

	public ClearLinkTask(Set<String> linkNames, LinkModificationSystem system, boolean atomic) {
		this.setLinkNames(linkNames);
		this.setSystem(system);
		this.setAtomic(atomic);
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

	public LinkModificationSystem getSystem() {
		return this.system;
	}

	public void setSystem(LinkModificationSystem system) {
		if (system == null) {
			throw new IllegalArgumentException("system cannot be null.");
		}

		this.system = system;
	}
	
	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	// MARK: Task
	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		List<LinkModificationSystemRequest> requests = this.buildRequests(input);
		
		LinkModificationSystemInstanceOptionsImpl options = new LinkModificationSystemInstanceOptionsImpl();

		try {
			LinkModificationSystemInstance instance = this.system.makeInstance(options);
			
			LinkModificationSystemUtility.queueRequests(requests, instance);
			
			instance.applyChanges();
		} catch (Exception e) {
			throw new FailedTaskException(e);
		}
	}

	private List<LinkModificationSystemRequest> buildRequests(ModelKeyListAccessor<T> input) {
		String modelType = input.getModelType();
		List<ModelKey> keys = input.getModelKeys();
		List<String> stringKeys = ModelKey.keysAsStrings(keys);
		
		RequestBuilderItem builder = ReusableLinkModificationSystemRequestBuilder.make(modelType);
		
		builder.setLinkChangeType(MutableLinkChangeType.CLEAR);
		
		RequestBuilderGroup builderGroup = builder.makeForPrimaryKeys(stringKeys).makeForLinkNames(this.linkNames);
		return builderGroup.makeRequests();
	}

	@Override
	public String toString() {
		return "ClearLinkTask [linkNames=" + this.linkNames + ", system=" + this.system + ", atomic="
		        + this.atomic + "]";
	}
}
