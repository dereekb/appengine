package com.dereekb.gae.model.extension.links.system.mutable;

import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used by {@link MutableLink} for providing results about any link changes.
 * 
 * @author dereekb
 *
 */
public interface MutableLinkChangeResult {

	/**
	 * Returns {@link ModelKey} values for all successful/non-redundant changes.
	 * If the set is not empty then the model was modified.
	 * <p>
	 * For {@link MutableLinkChangeType#SET}, this describes the original set being replaced.
	 * <p>
	 * For {@link MutableLinkChangeType#ADD}, this describes values that were
	 * set to be added, and were not already linked.
	 * <p>
	 * For {@link MutableLinkChangeType#REMOVE}, this describes values that
	 * were set to be removed, and were linked.
	 * <p>
	 * For {@link MutableLinkChangeType#CLEAR}, this describes all removed
	 * values.
	 *
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<ModelKey> getModified();

	/**
	 * Returns {@link ModelKey} values for all redundant changes.
	 * <p>
	 * For {@link MutableLinkChangeType#SET}, this describes values that were
	 * already set before being set again.
	 * <p>
	 * For {@link MutableLinkChangeType#ADD}, this describes values that were
	 * set to be added, but were already linked.
	 * <p>
	 * For {@link MutableLinkChangeType#REMOVE}, this describes values that
	 * were set to be removed, but were not linked.
	 * <p>
	 * For {@link MutableLinkChangeType#CLEAR}, this will return an empty
	 * {@link Set}.
	 *
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<ModelKey> getRedundant();

	/**
	 * Returns the dynamic information for this change.
	 * 
	 * @return {@link MutableLinkChangeResultDynamicInfo}. Never {@code null}.
	 */
	public MutableLinkChangeResultDynamicInfo getDynamicChangeInfo();

}
