package com.dereekb.gae.model.extension.links.system.modification;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.TypedLinkSystemComponent;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.keyed.Keyed;

/**
 * {@link LinkModificationSystemInstance} request to a model's links.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemRequest
        extends TypedLinkSystemComponent, Keyed<ModelKey> {
	
	/**
	 * Returns a unique optional key for this request.
	 * 
	 * @return {@link ModelKey}. Can be {@code null}.
	 */
	@Override
	public ModelKey keyValue();
	
	/**
	 * Returns the link name.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getLinkName();

	/**
	 * Returns the key of the primary model being modified.
	 * 
	 * @return {@link ModelKey}. Never {@code null}.
	 */
	public String getPrimaryKey();

	/**
	 * Returns the type of change to perform.
	 * 
	 * @return {@link MutableLinkChangeType}. Never {@code null}.
	 */
	public MutableLinkChangeType getLinkChangeType();

	/**
	 * Returns the type of the keys being linked for a dynamic request.
	 * 
	 * @return {@link String}. May be {@code null} for non-dynamic link changes.
	 */
	public String getDynamicLinkModelType();

	/**
	 * Returns the keys to change.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getKeys();
	
}
