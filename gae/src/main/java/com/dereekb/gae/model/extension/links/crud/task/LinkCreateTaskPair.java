package com.dereekb.gae.model.extension.links.crud.task;

import java.util.List;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
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
public class LinkCreateTaskPair<T extends UniqueModel> extends HandlerPair<CreatePair<T>, List<LinkSystemChange>> {

	public LinkCreateTaskPair(CreatePair<T> key, List<LinkSystemChange> object) {
		super(key, object);
	}

}
