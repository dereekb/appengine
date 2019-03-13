package com.dereekb.gae.server.app.model.app.shared.impl;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.misc.owned.AppOwnedModel;
import com.dereekb.gae.server.app.model.app.shared.AppRelatedModel;
import com.dereekb.gae.server.app.model.app.shared.MutableAppRelatedModel;
import com.dereekb.gae.server.datastore.models.DatedDatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyModelKeyUtil;
import com.dereekb.gae.server.datastore.objectify.model.MutableDatedObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Index;

/**
 * Abstract {@link AppRelatedModel} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractAppRelatedModel<T> extends DatedDatabaseModel
        implements MutableDatedObjectifyModel<T>, MutableAppRelatedModel, AppOwnedModel {

	private static final long serialVersionUID = 1L;

	protected static final ExtendedObjectifyModelKeyUtil<App> APP_UTIL = ExtendedObjectifyModelKeyUtil
	        .number(App.class);

	/**
	 * App for this model.
	 */
	@Index
	protected Key<App> app;

	public AbstractAppRelatedModel() {}

	@Override
	public Key<App> getApp() {
		return this.app;
	}

	@Override
	public void setApp(Key<App> app) {
		this.app = app;
	}

	// AppOwnedModel
	@Override
	public ModelKey getAppOwnerKey() {
		return ObjectifyModelKeyUtil.readModelKey(this.app);
	}

	// Objectify Model
	@Override
	public abstract Key<T> getObjectifyKey();

}
