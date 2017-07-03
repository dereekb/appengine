package com.dereekb.gae.model.extension.links.system.modification.components.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.Relation;
import com.dereekb.gae.model.extension.links.system.components.exceptions.NoRelationException;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkModification} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModificationImpl
        implements LinkModification {

	public static final boolean DEFAULT_OPTIONAL = false;

	private boolean optional;

	private ModelKey key;
	private LinkInfo link;
	private MutableLinkChange change;

	public LinkModificationImpl(ModelKey key, LinkInfo info, MutableLinkChange change) {
		this(key, info, change, DEFAULT_OPTIONAL);
	}

	public LinkModificationImpl(ModelKey key, LinkInfo link, MutableLinkChange change, boolean optional) {
		super();
		this.setOptional(optional);
		this.setKey(key);
		this.setLink(link);
		this.setChange(change);
	}

	@Override
	public boolean isOptional() {
		return this.optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	@Override
	public ModelKey getKey() {
		return this.key;
	}

	public void setKey(ModelKey key) {
		if (key == null) {
			throw new IllegalArgumentException("key cannot be null.");
		}

		this.key = key;
	}

	@Override
	public LinkInfo getLink() {
		return this.link;
	}

	public void setLink(LinkInfo link) {
		if (link == null) {
			throw new IllegalArgumentException("link cannot be null.");
		}

		this.link = link;
	}

	@Override
	public MutableLinkChange getChange() {
		return this.change;
	}

	public void setChange(MutableLinkChange change) {
		if (change == null) {
			throw new IllegalArgumentException("change cannot be null.");
		}

		this.change = change;
	}

	// MARK: Keyed
	@Override
	public ModelKey keyValue() {
		return this.key;
	}

	// MARK: TypedLinkSystemComponent
	@Override
	public String getLinkModelType() {
		return this.link.getLinkModelType();
	}

	// MARK: Utility
	public static List<LinkModification> makeSynchronizationLinkModifications(LinkModificationResult result) {
		LinkModification linkModification = result.getLinkModification();
		LinkInfo linkInfo = linkModification.getLink();
		Relation relation;

		try {
			relation = linkInfo.getRelationInfo();

			ModelKey originalKey = linkModification.getKey();
			MutableLinkChangeType originalChangeType = linkModification.getChange().getLinkChangeType();

			MutableLinkChangeResult linkChangeResult = result.getLinkChangeResult();
			Set<ModelKey> modified = linkChangeResult.getModified();
			LinkInfo relationLinkInfo = relation.getRelationLink();

			MutableLinkChangeType syncChangeType;

			switch (originalChangeType) {
				case CLEAR:
					// Treat as a removal for sync changes.
					originalChangeType = MutableLinkChangeType.REMOVE;
				case ADD:
				case REMOVE:
					syncChangeType = originalChangeType;

					MutableLinkChange syncChange = MutableLinkChangeImpl.make(syncChangeType, originalKey);
					return makeSynchronizationLinkModificationForKeys(syncChange, relationLinkInfo, modified);
				case SET:
					// Add and Remove to synchronize.

					Set<ModelKey> allAdded = new HashSet<ModelKey>(linkModification.getChange().getKeys());
					Set<ModelKey> alreadyLinked = linkChangeResult.getRedundant();
					allAdded.removeAll(alreadyLinked);	// Don't update links to
					                                  	// already linked.

					MutableLinkChange addChange = MutableLinkChangeImpl.make(MutableLinkChangeType.ADD, originalKey);
					List<LinkModification> addSync = makeSynchronizationLinkModificationForKeys(addChange,
					        relationLinkInfo, allAdded);

					MutableLinkChange removeChange = MutableLinkChangeImpl.make(MutableLinkChangeType.REMOVE,
					        originalKey);
					List<LinkModification> removeSync = makeSynchronizationLinkModificationForKeys(removeChange,
					        relationLinkInfo, modified);

					List<LinkModification> syncChanges = new ArrayList<LinkModification>(addSync);
					syncChanges.addAll(removeSync);

					return syncChanges;
				default:
					throw new UnsupportedOperationException();
			}
		} catch (NoRelationException e) {
			return Collections.emptyList();
		}
	}

	private static List<LinkModification> makeSynchronizationLinkModificationForKeys(MutableLinkChange syncChange,
	                                                                                 LinkInfo relationLinkInfo,
	                                                                                 Set<ModelKey> modelKeys) {
		List<LinkModification> modifications = new ArrayList<LinkModification>();

		for (ModelKey modelKey : modelKeys) {
			LinkModification modification = new LinkModificationImpl(modelKey, relationLinkInfo, syncChange);
			modifications.add(modification);
		}

		return modifications;
	}

}