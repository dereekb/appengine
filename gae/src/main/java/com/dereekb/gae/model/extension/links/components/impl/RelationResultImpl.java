package com.dereekb.gae.model.extension.links.components.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.RelationResult;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link RelationResult} implementation.
 *
 * @author dereekb
 *
 */
@Deprecated
public class RelationResultImpl
        implements RelationResult {

	private Set<ModelKey> hits;
	private Set<ModelKey> redundant;

	public RelationResultImpl() {
		this.hits = new HashSet<ModelKey>();
		this.redundant = new HashSet<ModelKey>();
	}

	public RelationResultImpl(Set<ModelKey> hits, Set<ModelKey> redundant) {
		this.setHits(hits);
		this.setRedundant(redundant);
	}

	public static RelationResult hit(ModelKey key) {
		Set<ModelKey> keys = new HashSet<ModelKey>(1);
		keys.add(key);
		return new RelationResultImpl(keys, null);
	}

	public static RelationResultImpl hits(List<ModelKey> hits) {
		return new RelationResultImpl(new HashSet<ModelKey>(hits), null);
	}

	public static RelationResult redundant(List<ModelKey> relationKeys) {
		return new RelationResultImpl(null, new HashSet<ModelKey>(relationKeys));
	}

	public void setHits(Set<ModelKey> hits) {
		if (hits == null) {
			hits = Collections.emptySet();
		}

		this.hits = hits;
	}

	public void setRedundant(Set<ModelKey> redundant) {
		if (redundant == null) {
			redundant = Collections.emptySet();
		}

		this.redundant = redundant;
	}

	// MARK: RelationResult
	@Override
	public Set<ModelKey> getHits() {
		return this.hits;
	}

	@Override
	public Set<ModelKey> getRedundant() {
		return this.redundant;
	}

	@Override
	public String toString() {
		return "RelationResultImpl [hits=" + this.hits + ", redundant=" + this.redundant + "]";
	}

}
