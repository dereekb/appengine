package com.dereekb.gae.model.extension.links.deprecated.functions;

/**
 * Delegate that gives the counter-type given an input type.
 * 
 * For example, the type if the primary object is a "parent" would be "child".
 * 
 * @author dereekb
 */
public interface BidirectionalLinkerFunctionTypeDelegate {

	public String linkerTypeForType(String type);

}
