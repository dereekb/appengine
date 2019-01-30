package com.dereekb.gae.model.extension.links.system.modification.components.impl;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;

/**
 * {@link LinkModificationResult} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModificationResultImpl
        implements LinkModificationResult {

	private boolean isSuccessful;
	private LinkModification linkModification;
	private MutableLinkChangeResult linkChangeResult;

	public LinkModificationResultImpl(boolean isSuccessful,
	        LinkModification linkModification,
	        MutableLinkChangeResult linkChangeResult) {
		super();
		this.setSuccessful(isSuccessful);
		this.setLinkModification(linkModification);
		this.setLinkChangeResult(linkChangeResult);
	}

	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public void setLinkChangeResult(MutableLinkChangeResult linkChangeResult) {
		if (linkChangeResult == null) {
			throw new IllegalArgumentException("linkChangeResult cannot be null.");
		}

		this.linkChangeResult = linkChangeResult;
	}

	public void setLinkModification(LinkModification linkModification) {
		if (linkModification == null) {
			throw new IllegalArgumentException("linkModification cannot be null.");
		}

		this.linkModification = linkModification;
	}

	// MARK: LinkModificationResult
	@Override
	public boolean isSuccessful() {
		return this.isSuccessful;
	}

	@Override
	public boolean isModelModified() {
		return this.isSuccessful() && this.linkChangeResult.getModified().isEmpty() == false;
	}

	@Override
	public LinkModification getLinkModification() {
		return this.linkModification;
	}

	@Override
	public MutableLinkChangeResult getLinkChangeResult() {
		return this.linkChangeResult;
	}
}
