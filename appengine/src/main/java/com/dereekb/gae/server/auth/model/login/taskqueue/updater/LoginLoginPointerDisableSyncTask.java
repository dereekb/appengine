package com.dereekb.gae.server.auth.model.login.taskqueue.updater;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdateType;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdaterResult;
import com.dereekb.gae.model.taskqueue.updater.impl.AbstractOneToIntermediaryToManyRelationUpdater;
import com.dereekb.gae.model.taskqueue.updater.impl.RelatedModelUpdaterResultImpl;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.search.query.LoginPointerQueryInitializer;
import com.dereekb.gae.server.auth.model.pointer.search.query.LoginPointerQueryInitializer.ObjectifyLoginPointerQuery;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.utility.StagedUpdater;
import com.dereekb.gae.server.datastore.utility.StagedUpdaterFactory;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.googlecode.objectify.Key;

/**
 * Updater task that synchronizes the disabled {@link LoginPointer} values if
 * the login is not synchronized.
 *
 * @author dereekb
 *
 */
public class LoginLoginPointerDisableSyncTask extends AbstractOneToIntermediaryToManyRelationUpdater<Login, Key<LoginPointer>, LoginPointer> {

	private StagedUpdaterFactory<LoginPointer> loginPointerUpdater;

	private ObjectifyRegistry<LoginPointer> loginPointerRegistry;
	private GetterSetter<Login> loginGetterSetter;

	public LoginLoginPointerDisableSyncTask(GetterSetter<Login> loginGetterSetter,
	        ObjectifyRegistry<LoginPointer> loginPointerRegistry,
	        StagedUpdaterFactory<LoginPointer> loginPointerUpdater) {
		super(loginGetterSetter, loginPointerRegistry);
		this.setLoginGetterSetter(loginGetterSetter);
		this.setLoginPointerRegistry(loginPointerRegistry);
		this.setLoginPointerUpdater(loginPointerUpdater);
	}

	public StagedUpdaterFactory<LoginPointer> getLoginPointerUpdater() {
		return this.loginPointerUpdater;
	}

	public void setLoginPointerUpdater(StagedUpdaterFactory<LoginPointer> loginPointerUpdater) {
		if (loginPointerUpdater == null) {
			throw new IllegalArgumentException("loginPointerUpdater cannot be null.");
		}

		this.loginPointerUpdater = loginPointerUpdater;
	}

	public ObjectifyRegistry<LoginPointer> getLoginPointerRegistry() {
		return this.loginPointerRegistry;
	}

	public void setLoginPointerRegistry(ObjectifyRegistry<LoginPointer> loginPointerRegistry) {
		if (loginPointerRegistry == null) {
			throw new IllegalArgumentException("loginPointerRegistry cannot be null.");
		}

		this.loginPointerRegistry = loginPointerRegistry;
	}

	public GetterSetter<Login> getLoginGetterSetter() {
		return this.loginGetterSetter;
	}

	public void setLoginGetterSetter(GetterSetter<Login> loginGetterSetter) {
		if (loginGetterSetter == null) {
			throw new IllegalArgumentException("loginGetterSetter cannot be null.");
		}

		this.loginGetterSetter = loginGetterSetter;
	}

	// MARK: AbstractOneToOneUpdater
	@Override
	protected Instance<Login> makeInstance(RelatedModelUpdateType change) {
		return new LoginLoginPointerCloserInstance();
	}

	protected class LoginLoginPointerCloserInstance extends SimpleOneByOneAbstractInstance {

		private final StagedUpdater<LoginPointer> loginPointerUpdater = LoginLoginPointerDisableSyncTask.this.loginPointerUpdater
		        .makeUpdater();

		// MARK: OneByOneAbstractInstance
		@Override
		protected boolean shouldPerformChangesWithModel(Login model,
		                                                boolean modelExists) {
			return model.isSynchronized() == false;
		}

		@Override
		protected HashMapWithList<Login, Key<LoginPointer>> loadIntermediaries(Iterable<Login> models)
		        throws NoChangesAvailableException {

			HashMapWithList<Login, Key<LoginPointer>> intermediaries = new HashMapWithList<Login, Key<LoginPointer>>();

			for (Login model : models) {
				ObjectifyLoginPointerQuery configurer = new LoginPointerQueryInitializer.ObjectifyLoginPointerQuery();
				configurer.setLogin(model);

				ObjectifyQueryRequestBuilder<LoginPointer> pointerQuery = LoginLoginPointerDisableSyncTask.this.loginPointerRegistry
				        .makeQuery();
				configurer.configure(pointerQuery);

				// Search all pings related to this activity
				List<Key<LoginPointer>> pingKeys = pointerQuery.buildExecutableQuery().queryObjectifyKeys();
				intermediaries.addAll(model, pingKeys);
			}

			return intermediaries;
		}

		@Override
		protected Collection<ModelKey> getRelatedModelKeys(Login model,
		                                                   List<Key<LoginPointer>> intermediaryModels) {
			return LoginLoginPointerDisableSyncTask.this.loginPointerRegistry.getObjectifyKeyConverter()
			        .convertTo(intermediaryModels);
		}

		@Override
		protected boolean performChangesForRelation(Login model,
		                                            LoginPointer relation) {
			if (model.getDisabled() != relation.getDisabled()) {
				relation.setDisabled(model.getDisabled());
				return true;
			} else {
				return false;
			}
		}

		@Override
		protected void saveChanges(Login model,
		                           List<LoginPointer> updated) {
			// Save Login Changes. Synchronize and reset auth if necessary.
			model.setSynchronized();

			if (model.getDisabled()) {
				model.setAuthReset(new Date());
			}

			LoginLoginPointerDisableSyncTask.this.loginGetterSetter.update(model);

			// Save Login Pointer Changes
			this.loginPointerUpdater.update(updated);
		}

		@Override
		protected RelatedModelUpdaterResult makeOutputForChanges() {
			return new RelatedModelUpdaterResultImpl(this.loginPointerUpdater);
		}

	}

}
