package com.dereekb.gae.test.model.extension.link.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.services.response.impl.ReadResponseImpl;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.model.impl.LinkModelImplDelegate;
import com.dereekb.gae.model.extension.links.components.model.impl.LinkModelSetImpl;
import com.dereekb.gae.model.extension.links.components.model.impl.LinkModelSetImplDelegate;
import com.dereekb.gae.model.extension.links.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.components.system.exception.UnknownReverseLinkException;
import com.dereekb.gae.model.extension.links.components.system.impl.bidirectional.BidirectionalLinkSystemEntry;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

public abstract class AbstractTestLinkSystemDelegate<T extends UniqueModel>
        implements LinkSystemEntry, LinkModelImplDelegate<T>, LinkModelSetImplDelegate<T>,
        BidirectionalLinkSystemEntry {

	private boolean useSingleSet = true;

	protected Map<String, String> reverseLinkNames = new HashMap<String, String>();

	/**
	 * Cached, singleton set.
	 */
	private LinkModelSetImpl<T> set;

	public boolean isUseSingleSet() {
		return this.useSingleSet;
	}

	public void setUseSingleSet(boolean useSingleSet) {
		this.useSingleSet = useSingleSet;
	}

	public LinkModelSetImpl<T> getSet() {
		return this.set;
	}

	public void setSet(LinkModelSetImpl<T> set) {
		this.set = set;
	}

	@Override
	public LinkModelSet makeSet() {
		LinkModelSet set;

		if (this.useSingleSet) {
			if (this.set == null) {
				this.set = new LinkModelSetImpl<T>(this, this);
			}
			set = this.set;
		} else {
			set = new LinkModelSetImpl<T>(this, this);
		}

		return set;
	}

	@Override
	public ReadResponse<T> readModels(Collection<ModelKey> keys) {
		List<T> models = new ArrayList<T>();

		for (ModelKey key : keys) {
			T model = this.makeModel(key);
			models.add(model);
		}

		ReadResponseImpl<T> response = new ReadResponseImpl<T>(models);
		return response;
	}

	public abstract T makeModel(ModelKey key);

	@Override
	public void saveModels(List<T> models) {
		for (@SuppressWarnings("unused")
		T model : models) {
			// model.save();
		}
	}

	@Override
	public abstract Map<String, Link> buildLinks(T model);

	@Override
	public abstract String getLinkModelType();

	// BidirectionalLinkSystemEntry
	@Override
	public boolean isBidirectionallyLinked(LinkInfo info) {
		String linkName = info.getLinkName();
		return this.reverseLinkNames.containsKey(linkName);
	}

	@Override
	public String getReverseLinkName(LinkInfo info) throws UnknownReverseLinkException {
		String linkName = info.getLinkName();

		String reverseLinkName = this.reverseLinkNames.get(linkName);

		if (reverseLinkName == null) {
			throw new UnknownReverseLinkException(info);
		}

		return reverseLinkName;
	}

}
