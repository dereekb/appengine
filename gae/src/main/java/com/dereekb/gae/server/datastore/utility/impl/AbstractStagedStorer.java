package com.dereekb.gae.server.datastore.utility.impl;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.datastore.ForceStorer;
import com.dereekb.gae.server.datastore.exception.StoreKeyedEntityException;
import com.dereekb.gae.server.datastore.utility.StagedStorer;
import com.dereekb.gae.utilities.collections.IteratorUtility;

/**
 * Abstract {@link StagedStorer} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractStagedStorer<T> extends AbstractStagedTransactionChange
        implements StagedStorer<T> {

	private final ForceStorer<T> storer;

	private Set<T> entities = new HashSet<T>();

	public AbstractStagedStorer(ForceStorer<T> storer) {
		super();
		this.storer = storer;
	}

	public Set<T> getEntities() {
		return this.entities;
	}

	// MARK: Storer
	@Override
	public void store(T entity) throws StoreKeyedEntityException {
		this.storer.store(entity);
		this.entities.add(entity);
	}

	@Override
	public void store(Iterable<T> entities) throws StoreKeyedEntityException {
		this.storer.store(entities);
		this.entities.addAll(IteratorUtility.iterableToList(entities));
	}

	@Override
	public void forceStore(T entity) {
		this.storer.forceStore(entity);
		this.entities.add(entity);
	}

	@Override
	public void forceStore(Iterable<T> entities) {
		this.storer.forceStore(entities);
		this.entities.addAll(IteratorUtility.iterableToList(entities));
	}

	// MARK: StagedStorer
	@Override
	protected void finishChangesWithEntities() {
		this.finishUpdateWithEntities(this.entities);
	}

	protected abstract void finishUpdateWithEntities(Set<T> entities);

}
