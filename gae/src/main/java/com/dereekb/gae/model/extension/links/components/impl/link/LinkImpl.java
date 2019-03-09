package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.impl.LinkDataImpl;
import com.dereekb.gae.model.extension.links.components.impl.RelationResultImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Link} implementation that uses a {@link LinkImplDelegate}.
 *
 * @author dereekb
 *
 */
public final class LinkImpl
        implements Link {

	private LinkInfo linkInfo;
	private LinkImplDelegate linkDelegate;

	public LinkImpl(LinkInfo linkInfo, LinkImplDelegate linkDelegate) {
		this.linkInfo = linkInfo;
		this.linkDelegate = linkDelegate;
	}

	public LinkInfo getLinkInfo() {
		return this.linkInfo;
	}

	public void setLinkInfo(LinkInfo linkInfo) {
		this.linkInfo = linkInfo;
	}

	public LinkImplDelegate getLinkDelegate() {
		return this.linkDelegate;
	}


    public void setLinkDelegate(LinkImplDelegate linkDelegate) {
		this.linkDelegate = linkDelegate;
	}

	@Override
	public String getLinkName() {
		return this.linkInfo.getLinkName();
	}

	@Override
	public ModelKey getLinkModelKey() {
		return this.linkInfo.getLinkModelKey();
	}

	@Override
	public LinkTarget getLinkTarget() {
		return this.linkInfo.getLinkTarget();
	}

	@Override
	public RelationResultImpl setRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		List<ModelKey> keys = this.linkDelegate.keys();
		RelationResultImpl result = this.clearRelations();
		this.addRelation(change);

		Set<ModelKey> redundant = new HashSet<ModelKey>(keys);
		redundant.retainAll(change.getRelationKeys());

		result.setRedundant(redundant);
		return result;
	}

	@Override
	public RelationResultImpl addRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		Set<ModelKey> hit = new HashSet<ModelKey>();
		Set<ModelKey> miss = new HashSet<ModelKey>();

		for (ModelKey key : change.getRelationKeys()) {
			if (this.linkDelegate.add(key)) {
				hit.add(key);
			} else {
				miss.add(key);
			}
		}

		return new RelationResultImpl(hit, miss);
	}

	@Override
	public RelationResultImpl removeRelation(Relation change) throws RelationChangeException {
		Set<ModelKey> hit = new HashSet<ModelKey>();
		Set<ModelKey> miss = new HashSet<ModelKey>();

		for (ModelKey key : change.getRelationKeys()) {
			if (this.linkDelegate.remove(key)) {
				hit.add(key);
			} else {
				miss.add(key);
			}
		}

		return new RelationResultImpl(hit, miss);
	}

	@Override
	public RelationResultImpl clearRelations() {
		List<ModelKey> keys = this.linkDelegate.keys();
		this.linkDelegate.clear();
		return RelationResultImpl.hits(keys);
	}

	@Override
	public LinkData getLinkData() {
		List<ModelKey> keys = this.linkDelegate.keys();
		LinkData linkData = new LinkDataImpl(this, keys);
		return linkData;
	}

}
