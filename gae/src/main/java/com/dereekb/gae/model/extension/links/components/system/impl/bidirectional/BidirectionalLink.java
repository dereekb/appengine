package com.dereekb.gae.model.extension.links.components.system.impl.bidirectional;

import java.util.List;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.impl.RelationImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Link} wrapper for a {@link BidirectionalLinkSystem}.
 *
 * @author dereekb
 */
public final class BidirectionalLink
        implements Link, LinkInfo {

	private BidirectionalLinkDelegate delegate;
	private Link link;

	public BidirectionalLink(BidirectionalLinkDelegate delegate, Link link) {
		this.delegate = delegate;
		this.link = link;
	}

	public BidirectionalLinkDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(BidirectionalLinkDelegate delegate) {
		this.delegate = delegate;
	}

	public Link getLink() {
		return this.link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	// Link
	@Override
	public ModelKey getLinkModelKey() {
		return this.link.getLinkModelKey();
	}

	@Override
	public String getLinkName() {
		return this.link.getLinkName();
	}

	@Override
	public LinkTarget getLinkTarget() {
		return this.link.getLinkTarget();
	}

	@Override
	public void addRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		List<Link> reverseLinks = this.loadReverseLinks(change, true);
		Relation selfRelation = this.getSelfRelation();

		for (Link link : reverseLinks) {
			link.addRelation(selfRelation);
		}

		this.link.addRelation(change);
	}

	@Override
	public void removeRelation(Relation change) throws RelationChangeException {
		List<Link> reverseLinks = this.loadReverseLinks(change, false);
		Relation selfRelation = this.getSelfRelation();

		for (Link link : reverseLinks) {
			link.removeRelation(selfRelation);
		}

		this.link.removeRelation(change);
	}

	private Relation getSelfRelation() {
		ModelKey key = this.getLinkModelKey();
		Relation relation = new RelationImpl(key);
		return relation;
	}

	@Override
	public LinkData getLinkData() {
		return this.link.getLinkData();
	}

	@Override
	public void clearRelations() {
		Relation relations = this.link.getLinkData();
		this.removeRelation(relations);
	}

	// Internal
	/**
	 * Returns {@link Link} instances for each of the models mentioned in the
	 * change.
	 *
	 * @param change
	 *            {@link Relation} to use to change.
	 * @param required
	 *            Whether or not all models should be loaded.
	 * @return {@link List} of {@link Link} available links.
	 *
	 * @throws UnavailableLinkException
	 *             if the required links are unavailable.
	 */
	private List<Link> loadReverseLinks(Relation change,
	                                    boolean required) throws UnavailableLinkException {
		List<ModelKey> keys = change.getRelationKeys();
		List<Link> reverseLinks = this.delegate.getReverseLinks(this, keys);

		if (required && keys.size() != reverseLinks.size()) {
			throw new UnavailableLinkException("Required reverse links were not available.");
		}

		return reverseLinks;
	}

}
