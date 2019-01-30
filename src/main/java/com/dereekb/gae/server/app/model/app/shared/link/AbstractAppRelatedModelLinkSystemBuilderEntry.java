package com.dereekb.gae.server.app.model.app.shared.link;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.system.components.SimpleLinkInfo;
import com.dereekb.gae.model.extension.links.system.components.impl.SimpleLinkInfoImpl;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkDataAssertionDelegate;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.impl.AbstractMutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.impl.assertions.AdminOnlyMutableLinkDataAssertionDelegate;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.SingleMutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.SingleMutableLinkDataDelegate;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.link.AppLinkSystemBuilderEntry;
import com.dereekb.gae.server.app.model.app.shared.MutableAppRelatedModel;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.googlecode.objectify.Key;

/**
 * {@link MutableLinkSystemBuilderEntry} implementation for
 * {@link MutableAppRelatedModel}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractAppRelatedModelLinkSystemBuilderEntry<T extends MutableAppRelatedModel> extends AbstractMutableLinkSystemBuilderEntry<T> {

	protected static final ExtendedObjectifyModelKeyUtil<App> APP_UTIL = ExtendedObjectifyModelKeyUtil
	        .number(App.class);

	private SimpleLinkInfo appLinkInfo = new SimpleLinkInfoImpl(AppLinkSystemBuilderEntry.LINK_MODEL_TYPE);

	public AbstractAppRelatedModelLinkSystemBuilderEntry(ReadService<T> readService,
	        Updater<T> updater,
	        TaskRequestSender<T> reviewTaskSender) {
		super(readService, updater, reviewTaskSender);
	}

	public SimpleLinkInfo getAppLinkInfo() {
		return this.appLinkInfo;
	}

	public void setAppLinkInfo(SimpleLinkInfo appLinkInfo) {
		if (appLinkInfo == null) {
			throw new IllegalArgumentException("appLinkInfo cannot be null.");
		}

		this.appLinkInfo = appLinkInfo;
	}

	// MARK: AbstractMutableLinkSystemBuilderEntry
	@Override
	protected List<MutableLinkData<T>> makeLinkData() {
		List<MutableLinkData<T>> linkData = new ArrayList<MutableLinkData<T>>();

		// App Link
		MutableLinkData<T> appLink = this.makeAppLink();
		linkData.add(appLink);

		return linkData;
	}

	protected MutableLinkData<T> makeAppLink() {

		SingleMutableLinkData<T> appLinkData = new SingleMutableLinkData<T>(this.appLinkInfo,
		        new SingleMutableLinkDataDelegate<T>() {

			        @Override
			        public ModelKey readLinkedModelKey(T model) {
				        Key<App> key = model.getApp();
				        return APP_UTIL.readKey(key);
			        }

			        @Override
			        public void setLinkedModelKey(T model,
			                                      ModelKey modelKey) {
				        Key<App> key = APP_UTIL.fromModelKey(modelKey);
				        model.setApp(key);
			        }

		        });

		// Tutor App
		MutableLinkDataAssertionDelegate<T> appAssertionDelegate = AdminOnlyMutableLinkDataAssertionDelegate.make();
		appLinkData.setAssertionDelegate(appAssertionDelegate);

		return appLinkData;
	}

	@Override
	public String toString() {
		return "AbstractAppRelatedModelLinkSystemBuilderEntry [appLinkInfo=" + this.appLinkInfo + "]";
	}

}
