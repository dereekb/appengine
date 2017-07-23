package com.dereekb.gae.model.extension.links.crud.task;

import java.util.List;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.pairs.HandlerPair;

/**
 * Pair used by {@link LinkCreateTaskImpl}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LinkCreateTaskPair<T extends UniqueModel> extends HandlerPair<CreatePair<T>, List<LinkModificationSystemRequest>> {

	public LinkCreateTaskPair(CreatePair<T> key, List<LinkModificationSystemRequest> object) {
		super(key, object);
	}

	@Override
	public String toString() {
		return "LinkCreateTaskPair [key=" + this.key + ", object=" + this.object + "]";
	}

}
