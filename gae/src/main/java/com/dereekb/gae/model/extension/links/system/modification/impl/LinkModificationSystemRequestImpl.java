package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkModificationSystemRequest} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemRequestImpl
        implements LinkModificationSystemRequest {

	private String linkName;
	private String linkModelType;
	private ModelKey primaryKey;

	private MutableLinkChange change;

	public LinkModificationSystemRequestImpl(String linkModelType,
	        ModelKey primaryKey,
	        String linkName,
	        MutableLinkChangeType linkChangeType,
	        Collection<ModelKey> keys) throws IllegalArgumentException {
		this(linkModelType, primaryKey, linkName, MutableLinkChangeImpl.make(linkChangeType, keys));
	}

	public LinkModificationSystemRequestImpl(String linkModelType,
	        ModelKey primaryKey,
	        String linkName,
	        MutableLinkChange change) throws IllegalArgumentException {
		this.setPrimaryKey(primaryKey);
		this.setLinkModelType(linkModelType);
		this.setLinkName(linkName);
		this.setChange(change);
	}

	@Override
	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String linkName) {
		if (linkName == null) {
			throw new IllegalArgumentException("linkName cannot be null.");
		}

		this.linkName = linkName;
	}

	@Override
	public String getLinkModelType() {
		return this.linkModelType;
	}

	public void setLinkModelType(String linkModelType) {
		if (linkModelType == null) {
			throw new IllegalArgumentException("linkModelType cannot be null.");
		}

		this.linkModelType = linkModelType;
	}

	@Override
	public ModelKey getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(ModelKey primaryKey) {
		if (primaryKey == null) {
			throw new IllegalArgumentException("primaryKey cannot be null.");
		}

		this.primaryKey = primaryKey;
	}

	public MutableLinkChange getChange() {
		return this.change;
	}

	public void setChange(MutableLinkChange change) {
		if (change == null) {
			throw new IllegalArgumentException("change cannot be null.");
		}

		this.change = change;
	}

	@Override
	public MutableLinkChangeType getLinkChangeType() {
		return this.change.getLinkChangeType();
	}

	@Override
	public Set<ModelKey> getKeys() {
		return this.change.getKeys();
	}

	@Override
	public String getDynamicLinkModelType() {
		return this.change.getDynamicLinkModelType();
	}

}
