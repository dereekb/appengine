package com.dereekb.gae.server.datastore.utility.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.datastore.utility.StagedUpdater;

/**
 * Abstract {@link StagedUpdater} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractStagedUpdater<T> extends AbstractStagedTransactionChange
        implements StagedUpdater<T> {

	private final Updater<T> updater;

	private Set<T> entities = new HashSet<T>();

	public AbstractStagedUpdater(Updater<T> updater) {
		super();
		this.updater = updater;
	}

	public Set<T> getEntities() {
		return this.entities;
	}

	// MARK: Updater
	@Override
	public boolean update(T entity) throws UpdateUnkeyedEntityException {
		if (this.updater.update(entity)) {
			this.entities.add(entity);
			return true;
		}
		
		return false;
	}

	@Override
	public List<T> update(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		List<T> updated = this.updater.update(entities);
		this.entities.addAll(updated);
		return updated;
	}

	@Override
	public boolean updateAsync(T entity) throws UpdateUnkeyedEntityException {
		if (this.updater.updateAsync(entity)) {
			this.entities.add(entity);
			return true;
		}
		
		return false;
	}

	@Override
	public List<T> updateAsync(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		List<T> updated = this.updater.updateAsync(entities);
		this.entities.addAll(updated);
		return updated;
	}

	// MARK: StagedUpdater
	@Override
	protected void finishChangesWithEntities() {
		this.finishUpdateWithEntities(this.entities);
	}

	protected abstract void finishUpdateWithEntities(Set<T> entities);

}
