package com.dereekb.gae.server.datastore.utility.impl;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.datastore.utility.StagedUpdater;
import com.dereekb.gae.server.datastore.utility.StagedUpdaterAlreadyFinishedException;
import com.dereekb.gae.utilities.collections.IteratorUtility;

/**
 * Abstract {@link StagedUpdater} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractStagedUpdater<T>
        implements StagedUpdater<T> {

	private final Updater<T> updater;

	private boolean complete = false;
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
	public void update(T entity) throws UpdateUnkeyedEntityException {
		this.entities.add(entity);
		this.updater.update(entity);
	}

	@Override
	public void update(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		this.entities.addAll(IteratorUtility.iterableToList(entities));
		this.updater.update(entities);
	}

	@Override
	public void updateAsync(T entity) throws UpdateUnkeyedEntityException {
		this.entities.add(entity);
		this.updater.updateAsync(entity);
	}

	@Override
	public void updateAsync(Iterable<T> entities) throws UpdateUnkeyedEntityException {
		this.entities.addAll(IteratorUtility.iterableToList(entities));
		this.updater.updateAsync(entities);
	}

	// MARK: StagedUpdater
	@Override
	public void finishUpdate() throws StagedUpdaterAlreadyFinishedException {
		if (this.complete) {
			throw new StagedUpdaterAlreadyFinishedException();
		}

		this.complete = true;
		this.finishUpdateWithEntities(this.entities);
	}

	protected abstract void finishUpdateWithEntities(Set<T> entities);

}
