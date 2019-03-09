package com.dereekb.gae.server.datastore;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.exception.UninitializedModelException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Interface for retrieving {@link UniqueModel} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model that implements the {@link UniqueModel} interface.
 * 
 * @see Setter
 */
public interface Getter<T extends UniqueModel> {

	/**
	 * Check that the model still exists.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@code true} if the model exists, false otherwise.
	 * @throws UninitializedModelException
	 *             thrown when a model without an identifier is provided.
	 */
	public boolean exists(T model) throws UninitializedModelException;

	/**
	 * Checks that the model exists.
	 *
	 * @param key
	 *            Identifier of the model. Never {@code null}.
	 * @return {@code true} if the object exists, false otherwise.
	 * @throws IllegalArgumentException
	 *             thrown when a {@code null} model key is provided.
	 */
	public boolean exists(ModelKey key) throws IllegalArgumentException;

	/**
	 * Checks that a corresponding model for each key exists.
	 *
	 * @param keys
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@code true} if all objects exist with the specified keys.
	 */
	public boolean allExist(Iterable<ModelKey> keys) throws IllegalArgumentException;

	/**
	 * Returns a set of keys of objects that exist in the database with the
	 * input keys.
	 *
	 * @param keys
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<ModelKey> getExisting(Iterable<ModelKey> keys) throws IllegalArgumentException;

	/**
	 * Retrieves the database model that corresponds to the input key.
	 *
	 * @param key
	 *            {@link ModelKey}. Never {@code null}.
	 * @return Model, or {@code null} if it doesn't exist.
	 */
	public T get(ModelKey key) throws IllegalArgumentException;

	/**
	 * Retrieves the database value of the input model, if it exists.
	 *
	 * @param model
	 *            Model. Never {@code null}.
	 * @return Model, or {@code null} if it doesn't exist.
	 */
	public T get(T model) throws UninitializedModelException;

	/**
	 * Retrieves database value of the specified models.
	 *
	 * @param models
	 *            {@link Iterable}. Never {@code null}.
	 * 
	 * @return A list of models that could be read from the source.
	 * @throws UninitializedModelException
	 *             if one or more models do not have an identifier.
	 */
	public List<T> get(Iterable<T> models) throws UninitializedModelException;

	/**
	 * Retrieves items with the given keys.
	 *
	 * @param keys
	 *            {@link Iterable}. Never {@code null}.
	 * @return A list of models that could be read from the source.
	 */
	public List<T> getWithKeys(Iterable<ModelKey> keys);

	/**
	 * Creates a new getter with the configuration.
	 * 
	 * @param config
	 *            {@link GetterConfiguration}. Never {@code null}.
	 * @return {@link Getter}. Never {@code null}.
	 */
	// public Getter<T> withConfiguration(GetterConfiguration config);

}
