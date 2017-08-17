package com.dereekb.gae.model.extension.links.system.modification.impl;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstanceOptions;

/**
 * {@link LinkModificationSystemInstanceOptions} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemInstanceOptionsImpl
        implements LinkModificationSystemInstanceOptions {
	
	private boolean atomic;
	private boolean autoCommit;

	public LinkModificationSystemInstanceOptionsImpl() {
		this(true);
	}
	
	public LinkModificationSystemInstanceOptionsImpl(boolean atomic) {
		this(atomic, true);
	}
	
	public LinkModificationSystemInstanceOptionsImpl(boolean atomic, boolean autoCommit) {
		super();
		this.atomic = atomic;
		this.autoCommit = autoCommit;
	}

	@Override
	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}
	
	@Override
	public boolean isAutoCommit() {
		return this.autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	@Override
	public String toString() {
		return "LinkModificationSystemInstanceOptionsImpl [atomic=" + this.atomic + "]";
	}
	
}
