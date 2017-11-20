package com.dereekb.gae.server.datastore;

import java.util.List;

import com.dereekb.gae.server.datastore.exception.UninitializedModelException;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Simple {@link Getter} for models that only perform gets.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SimpleGetter<T extends UniqueModel>
        extends SimpleKeyGetter<T> {

	/**
	 * Retrieves the database value of the input model, if it exists.
	 *
	 * @param model
	 *            Model. Never {@code null}.
	 * @return Model, or {@code null} if it doesn't exist.
	 * @throws UninitializedModelException
	 *             if the input model has no identifier.
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

}
