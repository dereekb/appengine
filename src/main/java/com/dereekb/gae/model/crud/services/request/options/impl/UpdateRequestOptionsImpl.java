package com.dereekb.gae.model.crud.services.request.options.impl;

import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * {@link UpdateRequestOptions} implementation.
 * 
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateRequestOptionsImpl extends AtomicRequestOptionsImpl
        implements UpdateRequestOptions {

	public UpdateRequestOptionsImpl() {
		super(true);
	}

	public UpdateRequestOptionsImpl(UpdateRequestOptions options) {
		super(options.isAtomic());
	}

	public UpdateRequestOptionsImpl(boolean atomic) {
		super(atomic);
	}

	@Override
	public String toString() {
		return "UpdateRequestOptionsImpl [isAtomic()=" + this.isAtomic() + "]";
	}

}
