package com.dereekb.gae.model.extension.links.components.impl.link;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Link} that wraps another link instance to add as an alias.
 *
 * @author dereekb
 *
 */
public class LinkAlias
        implements Link {

	public String aliasType;
	public Link link;

	@Override
	public String getLinkName() {
		return this.aliasType;
	}

	@Override
	public ModelKey getLinkModelKey() {
		return this.link.getLinkModelKey();
	}

	@Override
	public LinkTarget getLinkTarget() {
		return this.link.getLinkTarget();
	}

	@Override
	public void addRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		this.link.addRelation(change);
	}

	@Override
	public void removeRelation(Relation change) throws RelationChangeException {
		this.link.removeRelation(change);
	}

	@Override
	public LinkData getLinkData() {
		return this.link.getLinkData();
	}

	@Override
	public void clearRelations() {
		this.link.clearRelations();
	}
}
