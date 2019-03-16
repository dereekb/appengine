package com.dereekb.gae.server.datastore.objectify.core;

import com.dereekb.gae.server.datastore.Storer;

/**
 * Key enforcement used for the {@link Storer} within {@link ObjectifyDatabaseEntity}.
 * 
 * @author dereekb
 *
 */
public enum ObjectifyDatabaseEntityKeyEnforcement {
	
	/**
	 * Default Key Enforcement behaviors.
	 */
	DEFAULT,

	/**
	 * The key for newly created types must be provided.
	 */
	MUST_BE_PROVIDED,
	
	/**
	 * THe key for newly created types must be null.
	 */
	MUST_BE_NULL,

	/**
	 * Same as {@link #MUST_BE_PROVIDED} but the enforcer also checks the key is not associated with another model.
	 */
	MUST_BE_PROVIDED_AND_UNIQUE

}
