package com.dereekb.gae.web.api.shared.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Atomic {@link ApiRequest}.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtomicApiRequest<I> extends ApiRequest<I> {

	protected boolean atomic = true;

	public AtomicApiRequest() {
		super();
	}

	public AtomicApiRequest(List<I> data) {
		super(data);
	}

	public AtomicApiRequest(List<I> data, boolean atomic) {
		super(data);
		this.setAtomic(atomic);
	}

	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	@Override
	public String toString() {
		return "AtomicApiRequest [atomic=" + this.atomic + "]";
	}

}
