package com.dereekb.gae.model.extension.links.system.modification.components.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
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
	public static List<LinkModification> makeSynchronizationLinkModificationForKeys(MutableLinkChange syncChange,
	                                                                                 LinkInfo relationLinkInfo,
	                                                                                 Set<ModelKey> modelKeys,
	                                                                                 boolean optional) {
		List<LinkModification> modifications = new ArrayList<LinkModification>();

		for (ModelKey modelKey : modelKeys) {			
			LinkModification modification = new LinkModificationImpl(modelKey, relationLinkInfo, syncChange, optional);
			modifications.add(modification);
		}

		return modifications;
	}

	@Override
	public String toString() {
		return "LinkModificationImpl [optional=" + this.optional + ", key=" + this.key + ", link=" + this.link
		        + ", change=" + this.change + "]";
	}

}