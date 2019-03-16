package com.dereekb.gae.model.extension.links.system.modification;

/**
 * {@link LinkModificationReference} extension that allows setting the pre-test result.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationPreTestPair extends LinkModificationReference {

	/**
	 * Sets the result.
	 * 
	 * @param result {@link LinkModificationPreTestResultInfo}. Never {@code null}.
	 */
	public void setResultInfo(LinkModificationPreTestResultInfo result);

}
