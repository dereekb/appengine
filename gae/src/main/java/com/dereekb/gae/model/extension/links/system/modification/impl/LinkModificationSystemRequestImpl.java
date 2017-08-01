package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkModificationSystemRequest} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemRequestImpl
        implements LinkModificationSystemRequest {
	
	private ModelKey requestKey;

	private String linkName;
	private String linkModelType;

	private String primaryKey;
	private MutableLinkChangeType linkChangeType;
	private String dynamicLinkModelType;
	private Set<String> keys;

	public LinkModificationSystemRequestImpl() {};
	
	public LinkModificationSystemRequestImpl(String linkModelType,
	        ModelKey primaryKey,
            String linkName,
	        MutableLinkChange linkChange) throws IllegalArgumentException {
		this(linkModelType, ModelKey.readStringKey(primaryKey), linkName, linkChange.getLinkChangeType(),
		        ModelKey.keysAsStrings(linkChange.getKeys()), linkChange.getDynamicLinkModelType());
	}

	public LinkModificationSystemRequestImpl(
	        String linkModelType,
	        ModelKey primaryKey,
	        String linkName,
	        MutableLinkChangeType linkChangeType,
	        Collection<String> keys) throws IllegalArgumentException {
		this(linkModelType, ModelKey.readStringKey(primaryKey), linkName, linkChangeType, keys, null);
	}

	public LinkModificationSystemRequestImpl(
	        String linkModelType,
	        String primaryKey,
	        String linkName,
	        MutableLinkChangeType linkChangeType,
	        Collection<String> keys) throws IllegalArgumentException {
		this(linkModelType, primaryKey, linkName, linkChangeType, keys, null);
	}

	public LinkModificationSystemRequestImpl(
	        String linkModelType,
	        String primaryKey,
	        String linkName,
	        MutableLinkChangeType linkChangeType,
	        Collection<String> keys,
	        String dynamicLinkModelType) throws IllegalArgumentException {
		this.setLinkName(linkName);
		this.setLinkModelType(linkModelType);
		this.setPrimaryKey(primaryKey);
		this.setLinkChangeType(linkChangeType);
		this.setKeys(keys);
		this.setDynamicLinkModelType(dynamicLinkModelType);
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
	public String getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(ModelKey key) {
		this.setPrimaryKey(ModelKey.readStringKey(key));
	}

	public void setPrimaryKey(String primaryKey) {
		if (primaryKey == null) {
			throw new IllegalArgumentException("primaryKey cannot be null.");
		}

		this.primaryKey = primaryKey;
	}

	@Override
	public MutableLinkChangeType getLinkChangeType() {
		return this.linkChangeType;
	}

	public void setLinkChangeType(MutableLinkChangeType linkChangeType) {
		if (linkChangeType == null) {
			throw new IllegalArgumentException("linkChangeType cannot be null.");
		}

		this.linkChangeType = linkChangeType;
	}

	@Override
	public String getDynamicLinkModelType() {
		return this.dynamicLinkModelType;
	}

	public void setDynamicLinkModelType(String dynamicLinkModelType) {
		this.dynamicLinkModelType = dynamicLinkModelType;
	}

	@Override
	public Set<String> getKeys() {
		return this.keys;
	}

	public void setKeys(Collection<String> keys) {
		if (keys == null) {
			throw new IllegalArgumentException("keys cannot be null.");
		}

		this.keys = new HashSet<String>(keys);
	}

	@Override
	public ModelKey keyValue() {
		return this.requestKey;
	}

	public ModelKey getRequestKey() {
		return this.requestKey;
	}
	
	public void setRequestKey(ModelKey requestKey) {
		this.requestKey = requestKey;
	}

}
