package com.dereekb.gae.server.auth.model.key.link;

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
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.link.LoginPointerLinkSystemBuilderEntry;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.googlecode.objectify.Key;

/**
 * {@link MutableLinkSystemBuilderEntry} implementation for {@link LoginKey}.
 *
 * @author dereekb
 *
 */
public class LoginKeyLinkSystemBuilderEntry extends AbstractMutableLinkSystemBuilderEntry<LoginKey> {

	public static final String LINK_MODEL_TYPE = "LoginKey";

	private static final ExtendedObjectifyModelKeyUtil<LoginPointer> loginPointerUtil = ExtendedObjectifyModelKeyUtil
	        .make(LoginPointer.class, ModelKeyType.NAME);

	private SimpleLinkInfo loginPointerLinkInfo = new SimpleLinkInfoImpl(LoginPointerLinkSystemBuilderEntry.LINK_MODEL_TYPE);

	public LoginKeyLinkSystemBuilderEntry(ReadService<LoginKey> readService) {
		super(readService);
	}

	// MARK: AbstractMutableLinkSystemBuilderEntry
	@Override
	public String getLinkModelType() {
		return LINK_MODEL_TYPE;
	}

	@Override
	public ModelKeyType getModelKeyType() {
		return ModelKeyType.NUMBER;
	}

	@Override
	protected List<MutableLinkData<LoginKey>> makeLinkData() {
		List<MutableLinkData<LoginKey>> linkData = new ArrayList<MutableLinkData<LoginKey>>();

		// LoginKey Link
		SingleMutableLinkData<LoginKey> loginPointerLinkData = new SingleMutableLinkData<LoginKey>(this.loginPointerLinkInfo, new SingleMutableLinkDataDelegate<LoginKey>() {

			@Override
			public ModelKey readLinkedModelKey(LoginKey model) {
				Key<LoginPointer> key = model.getLoginPointer();
				return loginPointerUtil.toModelKey(key);
			}

			@Override
			public void setLinkedModelKey(LoginKey model,
			                              ModelKey modelKey) {
				Key<LoginPointer> key = loginPointerUtil.fromModelKey(modelKey);
				model.setLoginPointer(key);
			}
			
		});

		MutableLinkDataAssertionDelegate<LoginKey> adminDelegate = AdminOnlyMutableLinkDataAssertionDelegate.make();
		loginPointerLinkData.setAssertionDelegate(adminDelegate);
		
		linkData.add(loginPointerLinkData);
		
		return linkData;
	}

}
