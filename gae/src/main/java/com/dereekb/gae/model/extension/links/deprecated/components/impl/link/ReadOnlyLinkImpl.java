package com.dereekb.gae.model.extension.links.components.impl.link;

import com.dereekb.gae.model.extension.links.deprecated.components.LinkInfo;
import com.dereekb.gae.model.extension.links.deprecated.components.Relation;
import com.dereekb.gae.model.extension.links.deprecated.components.exception.IllegalRelationChangeException;
import com.dereekb.gae.model.extension.links.deprecated.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.deprecated.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.deprecated.components.impl.RelationResultImpl;

/**
 * {@link LinkImpl} extension that will throw
 * {@link IllegalRelationChangeException} when attempting to change any link.
 * 
 * @author dereekb
 *
 */
@Deprecated
public class ReadOnlyLinkImpl extends LinkImpl {

	public ReadOnlyLinkImpl(LinkInfo linkInfo, LinkImplDelegate linkDelegate) {
		super(linkInfo, linkDelegate);
	}

	// MARK: Link Impl
	@Override
	public RelationResultImpl setRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		throw new IllegalRelationChangeException(change);
	}

	@Override
	public RelationResultImpl addRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		throw new IllegalRelationChangeException(change);
	}

	@Override
	public RelationResultImpl removeRelation(Relation change) throws RelationChangeException {
		throw new IllegalRelationChangeException(change);
	}

	@Override
	public RelationResultImpl clearRelations() throws RelationChangeException {
		throw new IllegalRelationChangeException();
	}

}
