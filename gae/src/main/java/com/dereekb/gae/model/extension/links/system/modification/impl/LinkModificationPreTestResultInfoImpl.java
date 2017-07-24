package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPreTestResultInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


/**
 * {@link LinkModificationPreTestResultInfo} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModificationPreTestResultInfoImpl
        implements LinkModificationPreTestResultInfo {
	
	private boolean isMissingPrimaryKey;
	private Set<ModelKey> missingTargetKeys;

	public LinkModificationPreTestResultInfoImpl() {}
	
	public LinkModificationPreTestResultInfoImpl(boolean isMissingPrimaryKey,
	        Set<ModelKey> missingTargetKeys) {
		super();
		this.isMissingPrimaryKey = isMissingPrimaryKey;
		this.missingTargetKeys = missingTargetKeys;
	}

	// MARK: LinkModificationPreTestResultInfo
	@Override
	public boolean isPassed() {
		return !this.isMissingPrimaryKey && this.missingTargetKeys.isEmpty();
	}
	
	@Override
	public boolean isMissingPrimaryKey() {
		return this.isMissingPrimaryKey;
	}
	
	public void setMissingPrimaryKey(boolean isMissingPrimaryKey) {
		this.isMissingPrimaryKey = isMissingPrimaryKey;
	}
	
	@Override
	public Set<ModelKey> getMissingTargetKeys() {
		return this.missingTargetKeys;
	}
	
	public void setMissingTargetKeys(Set<ModelKey> missingTargetKeys) {
		this.missingTargetKeys = missingTargetKeys;
	}

	@Override
	public String toString() {
		return "LinkModificationPreTestResultInfoImpl [isMissingPrimaryKey=" + this.isMissingPrimaryKey
		        + ", missingTargetKeys=" + this.missingTargetKeys + "]";
	}

}
