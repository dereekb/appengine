package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.exception.LinkModelMismatchException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;

/**
 * Set of {@link LinkModificationSystemModelChange} instances.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemModelChangeSet {

	/**
	 * Whether or not all changes within this set are optional.
	 * 
	 * @return {@code true} if all changes are optional.
	 */
	public boolean isOptional();
	
	/**
	 * Sets all pairs within this change set as skipped.
	 */
	public void setSkipped();

	/**
	 * Creates a new instance that is linked to the specific model.
	 * <p>
	 * The instance is designed to be created on an idempotent loop, allowing this function to be called multiple times and each instance should work independently of eachother's previous result.
	 * 
	 * @param linkModel
	 *            {@link MutableLinkModel}. Never {@code null}.
	 * @return {@link LinkModificationSystemModelChangeInstance}. Never
	 *         {@code null}.
	 *         
	 * @throws LinkModelMismatchException thrown if the input model is not the same as the expected model.
	 */
	public LinkModificationSystemModelChangeInstanceSet makeInstanceWithModel(MutableLinkModel linkModel) throws LinkModelMismatchException;

}
