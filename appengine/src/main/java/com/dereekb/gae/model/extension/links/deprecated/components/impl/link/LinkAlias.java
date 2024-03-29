package com.dereekb.gae.model.extension.links.components.impl.link;

import com.dereekb.gae.model.extension.links.deprecated.components.Link;
import com.dereekb.gae.model.extension.links.deprecated.components.LinkData;
import com.dereekb.gae.model.extension.links.deprecated.components.LinkTarget;
import com.dereekb.gae.model.extension.links.deprecated.components.Relation;
import com.dereekb.gae.model.extension.links.deprecated.components.RelationResult;
import com.dereekb.gae.model.extension.links.deprecated.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.deprecated.components.exception.UnavailableLinkException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Link} that wraps another link instance to add as an alias.
 *
 * @author dereekb
 *
 */
@Deprecated
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

	// MARK: Link
	@Override
	public LinkData getLinkData() {
		return this.link.getLinkData();
	}

	@Override
	public RelationResult setRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		return this.link.setRelation(change);
	}

	@Override
	public RelationResult addRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		return this.link.addRelation(change);
	}

	@Override
	public RelationResult removeRelation(Relation change) throws RelationChangeException {
		return this.link.removeRelation(change);
	}

	@Override
	public RelationResult clearRelations() {
		return this.link.clearRelations();
	}

	@Override
	public String toString() {
		return "LinkAlias [aliasType=" + this.aliasType + ", link=" + this.link + "]";
	}

}
