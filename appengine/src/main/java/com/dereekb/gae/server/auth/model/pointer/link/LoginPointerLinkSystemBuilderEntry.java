package com.dereekb.gae.server.auth.model.pointer.link;

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
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.link.LoginLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.googlecode.objectify.Key;

/**
 * {@link MutableLinkSystemBuilderEntry} implementation for {@link LoginPointer}.
 *
 * @author dereekb
 *
 */
public class LoginPointerLinkSystemBuilderEntry extends AbstractMutableLinkSystemBuilderEntry<LoginPointer> {

	public static final String LINK_MODEL_TYPE = "LoginPointer";

	private static final ExtendedObjectifyModelKeyUtil<Login> loginUtil = ExtendedObjectifyModelKeyUtil
	        .make(Login.class, ModelKeyType.NUMBER);

	private SimpleLinkInfo loginLinkInfo = new SimpleLinkInfoImpl(LoginLinkSystemBuilderEntry.LINK_MODEL_TYPE);
	
	public LoginPointerLinkSystemBuilderEntry(ReadService<LoginPointer> readService,
	        Updater<LoginPointer> updater,
	        TaskRequestSender<LoginPointer> reviewTaskSender) {
		super(readService, updater, reviewTaskSender);
	}

	// MARK: AbstractMutableLinkSystemBuilderEntry
	@Override
	public String getLinkModelType() {
		return LINK_MODEL_TYPE;
	}

	@Override
	public ModelKeyType getModelKeyType() {
		return ModelKeyType.NAME;
	}

	@Override
	protected List<MutableLinkData<LoginPointer>> makeLinkData() {
		List<MutableLinkData<LoginPointer>> linkData = new ArrayList<MutableLinkData<LoginPointer>>();

		// Login Link
		SingleMutableLinkData<LoginPointer> loginLinkData = new SingleMutableLinkData<LoginPointer>(this.loginLinkInfo, new SingleMutableLinkDataDelegate<LoginPointer>() {

			@Override
			public ModelKey readLinkedModelKey(LoginPointer model) {
				Key<Login> key = model.getLogin();
				return loginUtil.toModelKey(key);
			}

			@Override
			public void setLinkedModelKey(LoginPointer model,
			                              ModelKey modelKey) {
				Key<Login> key = loginUtil.fromModelKey(modelKey);
				model.setLogin(key);
			}
			
		});

		MutableLinkDataAssertionDelegate<LoginPointer> adminDelegate = AdminOnlyMutableLinkDataAssertionDelegate.make();
		loginLinkData.setAssertionDelegate(adminDelegate);
		
		linkData.add(loginLinkData);
		
		return linkData;
	}

	@Override
	public String toString() {
		return "LoginPointerLinkSystemBuilderEntry [loginLinkInfo=" + this.loginLinkInfo + "]";
	}

}
