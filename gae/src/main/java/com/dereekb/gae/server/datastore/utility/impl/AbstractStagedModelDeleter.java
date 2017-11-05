package com.dereekb.gae.server.datastore.utility.impl;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.datastore.ModelDeleter;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.datastore.utility.StagedModelDeleter;
import com.dereekb.gae.utilities.collections.IteratorUtility;

/**
 * Abstract {@link StagedModelDeleter} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractStagedModelDeleter<T> extends AbstractStagedTransactionChange
        implements StagedModelDeleter<T> {

	private final ModelDeleter<T> deleter;

	private Set<T> entities = new HashSet<T>();

	public AbstractStagedModelDeleter(ModelDeleter<T> deleter) {
		super();
		this.deleter = deleter;
	}

	public Set<T> getEntities() {
		return this.entities;
	}

	// MARK: ModelDeleter
	@Override
	public void delete(T entity) throws UpdateUnkeyedEntityException {
		this.deleter.delete(entity);
		this.entities.add(entity);
	}

	@Override
	public void delete(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		this.deleter.delete(entities);
		this.entities.addAll(IteratorUtility.iterableToList(entities));
	}

	@Override
	public void deleteAsync(T entity) throws UpdateUnkeyedEntityException {
		this.deleter.deleteAsync(entity);
		this.entities.add(entity);
	}

	@Override
	public void deleteAsync(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		this.deleter.deleteAsync(entities);
		this.entities.addAll(IteratorUtility.iterableToList(entities));
	}

	// MARK: StagedModelDeleter
	@Override
	protected void finishChangesWithEntities() {
		this.finishUpdateWithEntities(this.entities);
	}

	protected abstract void finishUpdateWithEntities(Set<T> entities);

}
