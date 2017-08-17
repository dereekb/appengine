package com.dereekb.gae.model.extension.links.service;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Represents a change in the system.
 *
 * @author dereekb
 * 
 * @deprecated Replaced by {@link LinkModificationSystemRequest}.
 */
@Deprecated
public interface LinkSystemChange {

	/**
	 * @return {@link MutableLinkChangeType} to perform.
	 */
	public MutableLinkChangeType getAction();

	/**
	 * The type of the model to change.
	 *
	 * @return type of primary model.
	 */
	public String getPrimaryType();

	/**
	 * The key corresponding to the primary model to modify.
	 * <p>
	 * For example, if we wanted to change a model that had the name "x", this
	 * would return "x".
	 *
	 * @return {@link String} corresponding to the primary model to modify.
	 */
	public String getPrimaryKey();

	/**
	 * The name of the link in the primary model to change.
	 *
	 * @return name of the target {@link Link} to change.
	 */
	public String getLinkName();

	/**
	 * The set of keys to change, as a {@link String}.
	 * <p>
	 * Keys are passed as a {@link String} and are converted later to ensure
	 * that they are converted to the correct {@link ModelKey}.
	 *
	 * @return {@link Set} of string keys corresponding to target models.
	 */
	public Set<String> getTargetStringKeys();

}
