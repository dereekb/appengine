package com.dereekb.gae.server.app.model.app.link;

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
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.link.LoginLinkSystemBuilderEntry;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.googlecode.objectify.Key;

/**
 * {@link MutableLinkSystemBuilderEntry} implementation for {@link App}.
 *
 * @author dereekb
 *
 */
public class AppLinkSystemBuilderEntry extends AbstractMutableLinkSystemBuilderEntry<App> {

	public static final String LINK_MODEL_TYPE = "App";

	private static final ExtendedObjectifyModelKeyUtil<Login> loginUtil = ExtendedObjectifyModelKeyUtil
	        .make(Login.class, ModelKeyType.NUMBER);

	private SimpleLinkInfo loginLinkInfo = new SimpleLinkInfoImpl(LoginLinkSystemBuilderEntry.LINK_MODEL_TYPE);

	public AppLinkSystemBuilderEntry(ReadService<App> readService,
	        Updater<App> updater,
	        TaskRequestSender<App> reviewTaskSender) {
		super(readService, updater, reviewTaskSender);
	}

	// MARK: AbstractModelLinkSystemEntry
	@Override
	public String getLinkModelType() {
		return LINK_MODEL_TYPE;
	}

	@Override
	public ModelKeyType getModelKeyType() {
		return ModelKeyType.NUMBER;
	}

	@Override
	protected List<MutableLinkData<App>> makeLinkData() {
		List<MutableLinkData<App>> linkData = new ArrayList<MutableLinkData<App>>();

		// Login Link
		SingleMutableLinkData<App> loginLinkData = new SingleMutableLinkData<App>(this.loginLinkInfo, new SingleMutableLinkDataDelegate<App>() {

			@Override
			public ModelKey readLinkedModelKey(App model) {
				Key<Login> key = model.getLogin();
				return loginUtil.toModelKey(key);
			}

			@Override
			public void setLinkedModelKey(App model,
			                              ModelKey modelKey) {
				Key<Login> key = loginUtil.fromModelKey(modelKey);
				model.setLogin(key);
			}

		});

		MutableLinkDataAssertionDelegate<App> accountAdminDelegate = AdminOnlyMutableLinkDataAssertionDelegate.make();
		loginLinkData.setAssertionDelegate(accountAdminDelegate);
		linkData.add(loginLinkData);

		return linkData;
	}

}
