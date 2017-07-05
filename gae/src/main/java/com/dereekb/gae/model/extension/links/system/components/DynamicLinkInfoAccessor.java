package com.dereekb.gae.model.extension.links.system.components;

/**
 * {@link DynamicLinkInfo} accessor.
 * 
 * @author dereekb
 *
 * @see Link
 */
public interface DynamicLinkInfoAccessor {

	/**
	 * Returns dynamic information about the state of the link for the current
	 * {@link LinkModel}.
	 * <p>
	 * This is available for all links, not just those marked as
	 * {@link LinkType#DYNAMIC}.
	 * 
	 * @return {@link DynamicLinkInfo}. Never {@code null}.
	 */
	public DynamicLinkInfo getDynamicLinkInfo();
}
