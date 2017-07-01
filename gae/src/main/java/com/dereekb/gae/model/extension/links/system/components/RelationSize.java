package com.dereekb.gae.model.extension.links.system.components;

/**
 * Relationship type for a link.
 * 
 * @author dereekb
 */
public enum RelationSize {

	/**
	 * The link only has a single key attached. The related model has no notion
	 * of this model's existance.
	 */
	ONE_TO_NONE,

	/**
	 * The link has only a single key attached, and the relationship model also
	 * has a single key.
	 */
	ONE_TO_ONE,

	/**
	 * The link has only a single key attached, but has a parent model that
	 * references all the children.
	 */
	ONE_TO_MANY,

	/**
	 * The link has many keys attached to it.
	 */
	MANY_TO_ONE,

	/**
	 * The link has many keys attached to it.
	 */
	MANY_TO_MANY

}
