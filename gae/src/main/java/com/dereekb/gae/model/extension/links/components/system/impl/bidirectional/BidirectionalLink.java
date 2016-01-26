package com.dereekb.gae.model.extension.links.components.system.impl.bidirectional;

import java.util.List;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.RelationResult;
import com.dereekb.gae.model.extension.links.components.exception.NoReverseLinksException;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.impl.RelationImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Link} implementation. Wraps another {@link Link} for a
 * {@link BidirectionalLinkSystem}.
 *
 * @author dereekb
 */
public class BidirectionalLink
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

	// MARK: Link
	@Override
	public LinkData getLinkData() {
		return this.link.getLinkData();
	}

	@Override
	public RelationResult setRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		RelationResult results = this.clearRelations();
		this.addRelation(change);
		return results;
	}

	@Override
	public RelationResult addRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		Relation selfRelation = this.getSelfRelation();

		try {
			List<Link> reverseLinks = this.loadReverseLinks(change, true);

			for (Link link : reverseLinks) {
				link.addRelation(selfRelation);
			}
		} catch (NoReverseLinksException e) {
		}

		return this.link.addRelation(change);
	}

	@Override
	public RelationResult removeRelation(Relation change) throws RelationChangeException {
		Relation selfRelation = this.getSelfRelation();

		try {
			List<Link> reverseLinks = this.loadReverseLinks(change, false);

			for (Link link : reverseLinks) {
				link.removeRelation(selfRelation);
			}
		} catch (NoReverseLinksException e) {
		}

		return this.link.removeRelation(change);
	}

	private Relation getSelfRelation() {
		ModelKey key = this.getLinkModelKey();
		Relation relation = new RelationImpl(key);
		return relation;
	}

	@Override
	public RelationResult clearRelations() {
		Relation relations = this.link.getLinkData();
		return this.removeRelation(relations);
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
	 * @throws NoReverseLinksException
	 *             if there are no reverse links for this link.
	 * @throws UnavailableLinkException
	 *             if the required links are unavailable.
	 */
	private List<Link> loadReverseLinks(Relation change,
	                                    boolean required) throws NoReverseLinksException, UnavailableLinkException {
		List<ModelKey> keys = change.getRelationKeys();
		List<Link> reverseLinks = this.delegate.getReverseLinks(this, keys);

		if (required && keys.size() != reverseLinks.size()) {
			throw new UnavailableLinkException("Required reverse links were not available for change: " + change);
		}

		return reverseLinks;
	}

	@Override
	public String toString() {
		return "BidirectionalLink [delegate=" + this.delegate + ", link=" + this.link + "]";
	}

}
