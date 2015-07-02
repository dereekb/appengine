package com.dereekb.gae.model.extension.links.components.exception;

import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Exception that occurs when a relation change experiences an error.
 *
 * @author dereekb
 *
 */
public class RelationChangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ModelKey primary;
	private final Relation relation;

	public RelationChangeException(ModelKey primary, Relation relation) {
		this(primary, relation, null);
	}

	public RelationChangeException(ModelKey primary, Relation relation, Throwable cause) {
		super(cause);
		this.primary = primary;
		this.relation = relation;
	}

	public ModelKey getPrimary() {
		return this.primary;
	}

	public Relation getRelation() {
		return this.relation;
	}

}
