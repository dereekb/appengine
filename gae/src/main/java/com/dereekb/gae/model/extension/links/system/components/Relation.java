package com.dereekb.gae.model.extension.links.system.components;

/**
 * Relation that links together two {@link LinkInfo} values.
 * 
 * @author dereekb
 *
 */
public interface Relation {

	/**
	 * Returns the relation size for this relation.
	 * 
	 * @return {@link RelationSize}. Never {@code null}.
	 */
	public RelationSize getRelationSize();

	/**
	 * Returns the opposite link info represented by the other type.
	 * 
	 * @return {@link LinkInfo}. Never {@code null}.
	 */
	public LinkInfo getRelationLink();

}
