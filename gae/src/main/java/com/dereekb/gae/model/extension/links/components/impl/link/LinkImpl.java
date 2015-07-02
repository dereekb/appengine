package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.List;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.impl.LinkDataImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Link} implementation that uses a {@link LinkImplDelegate}.
 *
 * @author dereekb
 *
 */
public class LinkImpl
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
	public void addRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		for (ModelKey key : change.getRelationKeys()) {
			this.linkDelegate.add(key);
		}
	}

	@Override
	public void removeRelation(Relation change) throws RelationChangeException {
		for (ModelKey key : change.getRelationKeys()) {
			this.linkDelegate.remove(key);
		}
	}

	@Override
	public LinkData getLinkData() {
		List<ModelKey> keys = this.linkDelegate.keys();
		LinkData linkData = new LinkDataImpl(this, keys);
		return linkData;
	}

	@Override
	public void clearRelations() {
		this.linkDelegate.clear();
	}

}
