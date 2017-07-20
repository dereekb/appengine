package com.dereekb.gae.model.extension.links.system.mutable.impl;

import com.dereekb.gae.model.extension.links.system.components.Link;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeUndoBuilder;

/**
 * {@link MutableLinkChangeUndoBuilder} implementation.
 * 
 * @author dereekb
 *
 */
public class MutableLinkChangeUndoBuilderImpl implements MutableLinkChangeUndoBuilder {
	
	public static final MutableLinkChangeUndoBuilder SINGLETON = new  MutableLinkChangeUndoBuilderImpl();

	// MARK: MutableLinkChangeUndoBuilder
	@Override
	public MutableLinkChange makeUndo(Link currentLink,
	                                  MutableLinkChange previousChange,
	                                  MutableLinkChangeResult result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "MutableLinkChangeUndoBuilderImpl []";
	}

}
