package com.dereekb.gae.model.extension.links.components.model.impl;

import java.util.Collection;
import java.util.Map;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelChange;
import com.dereekb.gae.model.extension.links.components.model.change.impl.LinkModelChangeImpl;
import com.dereekb.gae.model.extension.links.components.model.change.impl.LinkModelLinkWrapper;
import com.dereekb.gae.model.extension.links.components.model.change.impl.LinkModelLinkWrapper.WrapperPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link LinkModel}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class LinkModelImpl<T extends UniqueModel>
        implements LinkModel {

	private LinkModelChange linkChanges;
	private Map<String, Link> links;

	private final T model;
	private final LinkModelImplDelegate<T> delegate;

	public LinkModelImpl(T model, LinkModelImplDelegate<T> delegate) {
		this.model = model;
		this.delegate = delegate;
	}

	public T getModel() {
		return this.model;
	}

	public LinkModelImplDelegate<T> getDelegate() {
		return this.delegate;
	}

	// LinkModel
	@Override
	public ModelKey getModelKey() {
		return this.model.getModelKey();
	}

	@Override
	public ModelKey getKeyValue() {
		return this.getModelKey();
	}

	@Override
	public String getType() {
		return this.delegate.getLinkModelType();
	}

	@Override
	public Link getLink(String name) throws UnavailableLinkException {
		Map<String, Link> map = this.getLinksMap();
		Link link = map.get(name);

		if (link == null) {
			throw UnavailableLinkException.withLink(name);
		}

		return link;
	}

	@Override
	public Collection<Link> getLinks() {
		Map<String, Link> map = this.getLinksMap();
		return map.values();
	}

	@Override
	public LinkModelChange getModelChanges() {
		if (this.linkChanges == null) {
			this.linkChanges = LinkModelChangeImpl.empty(this);
		}

		return this.linkChanges;
	}

	// Internal
	private Map<String, Link> getLinksMap() {
		if (this.links == null) {
			Map<String, Link> links = this.delegate.buildLinks(this.model);

			WrapperPair pair = LinkModelLinkWrapper.wrapLinks(this, links);
			this.links = pair.getWrappedLinks();
			this.linkChanges = pair.getLinkChanges();
		}

		return this.links;
	}

	@Override
	public String toString() {
		return "LinkModelImpl [links=" + this.links + ", model=" + this.model + ", delegate=" + this.delegate + "]";
	}

}
