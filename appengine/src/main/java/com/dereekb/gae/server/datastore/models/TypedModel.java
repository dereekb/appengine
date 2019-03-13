package com.dereekb.gae.server.datastore.models;

/**
 * Interface that represents a model or models of a specific type.
 * 
 * @author dereekb
 *
 */
public interface TypedModel {

	/**
	 * Returns the model type.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getModelType();

}
