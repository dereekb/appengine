package com.dereekb.gae.server.auth.model.key.link;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.impl.LinkInfoImpl;
import com.dereekb.gae.model.extension.links.components.impl.LinkTargetImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.LinkImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.ReadOnlyLinkImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.SingleLink;
import com.dereekb.gae.model.extension.links.components.impl.link.SingleLinkDelegate;
import com.dereekb.gae.model.extension.links.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.impl.AbstractModelLinkSystemEntry;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.link.LoginPointerLinkSystemEntry;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.googlecode.objectify.Key;

/**
 * {@link LinkSystemEntry} implementation for {@link LoginKey}.
 *
 * @author dereekb
 *
 */
@Deprecated
public class LoginKeyLinkSystemEntry extends AbstractModelLinkSystemEntry<LoginKey> {

	public static final String LOGIN_KEY_LINK_TYPE = "LoginKey";

	private static final ExtendedObjectifyModelKeyUtil<LoginPointer> loginPointerUtil = ExtendedObjectifyModelKeyUtil
	        .make(LoginPointer.class, ModelKeyType.NAME);

	private String loginPointerLinkName = LoginPointerLinkSystemEntry.LOGIN_POINTER_LINK_TYPE;

	private LinkTarget loginPointerTarget = new LinkTargetImpl(LoginPointerLinkSystemEntry.LOGIN_POINTER_LINK_TYPE,
	        ModelKeyType.NAME);

	public LoginKeyLinkSystemEntry(CrudService<LoginKey> crudService, Updater<LoginKey> updater) {
		super(LOGIN_KEY_LINK_TYPE, crudService, crudService, updater);
	}

	// MARK: AbstractModelLinkSystemEntry
	@Override
	public List<Link> getLinks(final LoginKey model) {
		List<Link> links = new ArrayList<Link>();

		ModelKey key = model.getModelKey();

		// Login Pointer Link
		LinkInfoImpl loginPointerLinkInfo = new LinkInfoImpl(this.loginPointerLinkName, key, this.loginPointerTarget);
		LinkImpl loginPointerLink = new ReadOnlyLinkImpl(loginPointerLinkInfo, new SingleLink(new SingleLinkDelegate() {

			@Override
			public ModelKey getKey() {
				return model.getPointerModelKey();
			}

			@Override
			public void setKey(ModelKey modelKey) {
				Key<LoginPointer> key = loginPointerUtil.fromModelKey(modelKey);
				model.setLoginPointer(key);
			}

		}));

		links.add(loginPointerLink);

		return links;
	}

}
