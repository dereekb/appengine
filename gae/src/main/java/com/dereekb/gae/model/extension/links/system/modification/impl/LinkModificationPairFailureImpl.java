package com.dereekb.gae.model.extension.links.system.modification.impl;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPairFailure;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPairFailureReason;
import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;

/**
 * {@link LinkModificationPairFailure} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModificationPairFailureImpl
        implements LinkModificationPairFailure {
	
	private static final LinkModificationPairFailure UNAVAILABLE_SINGLETON = new LinkModificationPairFailureImpl(LinkModificationPairFailureReason.UNAVAILABLE_MODEL); 
	
	private LinkModificationPairFailureReason reason;
	private ApiResponseErrorConvertable error;

	protected LinkModificationPairFailureImpl(LinkModificationPairFailureReason reason) {
		super();
		this.setReason(reason);
	}

	public LinkModificationPairFailureImpl(LinkModificationPairFailureReason reason,
	        ApiResponseErrorConvertable error) {
		super();
		this.setReason(reason);
		this.setError(error);
	}

	public static LinkModificationPairFailure unavailable() {
		return UNAVAILABLE_SINGLETON;
	}
	
	// MARK: LinkModificationPairFailure
	@Override
	public LinkModificationPairFailureReason getReason() {
		return this.reason;
	}
	
	public void setReason(LinkModificationPairFailureReason reason) {
		if (reason == null) {
			throw new IllegalArgumentException("Reason cannot be null.");
		}
	
		this.reason = reason;
	}
	
	@Override
	public ApiResponseErrorConvertable getError() {
		return this.error;
	}
	
	public void setError(ApiResponseErrorConvertable error) {
		if (error == null && this.reason == LinkModificationPairFailureReason.ERROR) {
			throw new IllegalArgumentException("Error cannot be null when reason is ERROR.");
		}
	
		this.error = error;
	}

	@Override
	public String toString() {
		return "LinkModificationPairFailureImpl [reason=" + this.reason + ", error=" + this.error + "]";
	}

}
