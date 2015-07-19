package com.dereekb.gae.model.extension.links.components.system.impl.bidirectional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkModel} implementation for a {@link BidirectionalLinkModel}.
 *
 * @author dereekb
 */
public final class BidirectionalLinkModel
        implements LinkModel, BidirectionalLinkDelegate {

	private final BidirectionalLinkModelDelegate delegate;
	private final LinkModel model;

	public BidirectionalLinkModel(BidirectionalLinkModelDelegate delegate, LinkModel model) {
		this.delegate = delegate;
		this.model = model;
	}

	public BidirectionalLinkModelDelegate getDelegate() {
		return this.delegate;
	}

	public LinkModel getModel() {
		return this.model;
	}

	// LinkModel
	@Override
	public ModelKey getModelKey() {
		return this.model.getModelKey();
	}

	@Override
	public String getType() {
		return this.model.getType();
	}

	@Override
	public Link getLink(String name) throws UnavailableLinkException {
		Link link = this.model.getLink(name);
		BidirectionalLink wrappedLink = new BidirectionalLink(this, link);
		return wrappedLink;
	}

	@Override
	public Collection<Link> getLinks() {
		Collection<Link> rawLinks = this.model.getLinks();
		List<Link> wrappedLinks = new ArrayList<Link>();

		for (Link link : rawLinks) {
			BidirectionalLink wrappedLink = new BidirectionalLink(this, link);
			wrappedLinks.add(wrappedLink);
		}

		return wrappedLinks;
	}

	@Override
	public LinkModelChange getModelChanges() {
		return this.model.getModelChanges();
	}

	// BidirectionalLinkDelegate
	@Override
	public List<Link> getReverseLinks(LinkInfo info,
	                                  List<ModelKey> keys) throws UnavailableLinkException {

		List<LinkModel> models = this.delegate.getReverseLinkModels(info, keys);
		List<Link> links = new ArrayList<Link>();

		if (models.isEmpty() == false) {
			String reverseLinkName = this.delegate.getReverseLinkName(info);

			if (reverseLinkName == null) {
				throw new UnavailableLinkException("Reverse link for link '" + info.getLinkName() + "' on type '"
				        + this.getType() + "' is not registered.");
			}

			for (LinkModel model : models) {
				Link link = model.getLink(reverseLinkName);
				links.add(link);
			}
		}

		return links;
	}

	@Override
	public String toString() {
		return "BidirectionalLinkModel [delegate=" + this.delegate + ", model=" + this.model + "]";
	}

}
