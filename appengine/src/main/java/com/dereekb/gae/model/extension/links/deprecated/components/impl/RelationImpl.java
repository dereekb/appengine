package com.dereekb.gae.model.extension.links.components.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.links.deprecated.components.Relation;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Relation} implementation.
 *
 * @author dereekb
 */
@Deprecated
public class RelationImpl
        implements Relation {

	private String relationTargetType;
	private List<ModelKey> relationKeys;

	public RelationImpl() {};

	public RelationImpl(ModelKey key) {
		this.setRelationKey(key);
	}

	public RelationImpl(Collection<ModelKey> keys) throws IllegalArgumentException {
		this.setRelationKeys(keys);
	}

	// Relation
	@Override
	public String getRelationTargetType() {
		return this.relationTargetType;
	}

	public void setRelationTargetType(String relationTargetType) {
		this.relationTargetType = relationTargetType;
	}

	@Override
	public List<ModelKey> getRelationKeys() {
		return this.relationKeys;
	}

	public void setRelationKey(ModelKey key) {
		this.relationKeys = new ArrayList<ModelKey>();

		if (key != null) {
			this.relationKeys.add(key);
		}
	}

	public void setRelationKeys(Collection<ModelKey> keys) {
		if (keys == null) {
			throw new IllegalArgumentException("Keys cannot be null.");
		}

		this.relationKeys = new ArrayList<ModelKey>(keys);
	}

	@Override
	public String toString() {
		return "RelationImpl [relationTargetType=" + this.relationTargetType + ", relationKeys=" + this.relationKeys
		        + "]";
	}

}
