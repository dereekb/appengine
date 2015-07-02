package com.dereekb.gae.model.extension.links.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.model.impl.LinkModelImplDelegate;
import com.dereekb.gae.model.extension.links.components.model.impl.LinkModelSetImpl;
import com.dereekb.gae.model.extension.links.components.model.impl.LinkModelSetImplDelegate;
import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.model.extension.links.components.system.exception.UnknownReverseLinkException;
import com.dereekb.gae.model.extension.links.components.system.impl.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.components.system.impl.bidirectional.BidirectionalLinkSystemEntry;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;

/**
 * Abstract implementation of different {@link LinkSystem} related elements.
 *
 * @author dereekb
 *
 * @param <T>
 */
public abstract class AbstractModelLinkSystemEntry<T extends UniqueModel>
        implements LinkSystemEntry, LinkModelImplDelegate<T>, LinkModelSetImplDelegate<T>, BidirectionalLinkSystemEntry {

	protected String modelType;

	protected ReadService<T> service;
	protected ConfiguredSetter<T> setter;

	/**
	 * Names for the reverse element.
	 *
	 * Keyed by this element's link names to the opposite link name.
	 *
	 * For example, if this has a link named "parent", the value "child" will be
	 * keyed to "parent".
	 */
	protected Map<String, String> reverseLinkNames = new HashMap<String, String>();

	public AbstractModelLinkSystemEntry(String modelType,
	        ReadService<T> service,
	        ConfiguredSetter<T> setter) {
		this.modelType = modelType;
		this.service = service;
		this.setter = setter;
	}

	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public ReadService<T> getService() {
		return this.service;
	}

	public void setService(ReadService<T> service) {
		this.service = service;
	}

	public ConfiguredSetter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(ConfiguredSetter<T> setter) {
		this.setter = setter;
	}

	public Map<String, String> getReverseLinkNames() {
		return this.reverseLinkNames;
	}

	public void setReverseLinkNames(Map<String, String> reverseLinkNames) {
		this.reverseLinkNames = reverseLinkNames;
	}

	@Override
    public ReadResponse<T> readModels(Collection<ModelKey> keys) {
		ReadRequest<T> request = new KeyReadRequest<T>(keys);
		request.getOptions().setAtomic(false);
		return this.service.read(request);
    }

	@Override
    public void saveModels(List<T> models) {
		this.setter.save(models);
    }

	@Override
    public String getLinkModelType() {
		return this.modelType;
    }

	// LinkModelImplDelegate
	@Override
	public Map<String, Link> buildLinks(T model) {
		Map<String, Link> map = new HashMap<String, Link>();
		List<Link> links = this.makeLinksForModel(model);

		for (Link link : links) {
			String key = link.getLinkName();
			map.put(key, link);
		}

		return map;
	}

	// AbstractLinkDelegateImpl
	public abstract List<Link> makeLinksForModel(T model);

	@Override
	public LinkModelSet makeSet() {
		return new LinkModelSetImpl<T>(this, this);
	}

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
