package com.dereekb.gae.model.extension.links.components.model.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.links.components.exception.LinkSaveConditionException;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelSetChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


/**
 * Delegate for {@link LinkModelSetImpl}.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface LinkModelSetImplDelegate<T> {

	/**
	 * Retrieves models from the database.
	 *
	 * @param keys
	 *            {@link Collection} of {@link ModelKey} values to read.
	 * @return {@link ReadResponse} for the models. Never {@code null}.
	 */
	public ReadResponse<T> readModels(Collection<ModelKey> keys);

	/**
	 * Validates the input models, throwing a {@link LinkSaveConditionException}
	 * when an invalid state is checked.
	 *
	 * @param models
	 *            Models to validate. Never {@code null}.
	 * @param changes
	 *            {@link LinkModelSetChange} containing all changes made to the
	 *            {@link LinkModelSet}. Use this for validating models.
	 * @throws LinkSaveConditionException
	 */
	public void validateModels(List<T> models,
	                           LinkModelSetChange changes) throws LinkSaveConditionException;

	/**
	 * Saves the input models. This function should never result in an
	 * exception, as it may damage the database integrity by leaving models in
	 * an inconsistent state.
	 * <p>
	 * Additional functions should be carried out in
	 * {@link #reviewModels(List, LinkModelSetChange)}.
	 *
	 * @param models
	 *            Models to save. Never {@code null}.
	 */
	public void saveModels(List<T> models);

	/**
	 * Called after {@link #saveModels(List)} is performed. This allows the
	 * delegate to perform additional calls.
	 *
	 * @param models
	 *            Models to review. Never {@code null}.
	 * @param changes
	 *            {@link LinkModelSetChange} containing all changes made to the
	 *            {@link LinkModelSet}.
	 */
	public void reviewModels(List<T> models,
	                         LinkModelSetChange changes);

}
