package com.dereekb.gae.server.auth.model.pointer.link;

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
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.link.LoginLinkSystemEntry;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.googlecode.objectify.Key;

/**
 * {@link LinkSystemEntry} implementation for {@link LoginPointer}.
 *
 * @author dereekb
 *
 */
@Deprecated
public class LoginPointerLinkSystemEntry extends AbstractModelLinkSystemEntry<LoginPointer> {

	public static final String LOGIN_POINTER_LINK_TYPE = "LoginPointer";

	private static final ExtendedObjectifyModelKeyUtil<Login> loginUtil = ExtendedObjectifyModelKeyUtil
	        .make(Login.class, ModelKeyType.NUMBER);

	private String loginLinkName = LoginLinkSystemEntry.LOGIN_LINK_TYPE;

	private LinkTarget loginTarget = new LinkTargetImpl(LoginLinkSystemEntry.LOGIN_LINK_TYPE, ModelKeyType.NUMBER);

	public LoginPointerLinkSystemEntry(CrudService<LoginPointer> crudService, Updater<LoginPointer> updater) {
		super(LOGIN_POINTER_LINK_TYPE, crudService, crudService, updater);
	}

	// MARK: AbstractModelLinkSystemEntry
	@Override
	public List<Link> getLinks(final LoginPointer model) {
		List<Link> links = new ArrayList<Link>();

		ModelKey key = model.getModelKey();

		// Login Link
		LinkInfoImpl loginLinkInfo = new LinkInfoImpl(this.loginLinkName, key, this.loginTarget);
		LinkImpl loginLink = new ReadOnlyLinkImpl(loginLinkInfo, new SingleLink(new SingleLinkDelegate() {

			@Override
			public ModelKey getKey() {
				Key<Login> key = model.getLogin();
				return loginUtil.toModelKey(key);
			}

			@Override
			public void setKey(ModelKey modelKey) {
				Key<Login> key = loginUtil.fromModelKey(modelKey);
				model.setLogin(key);
			}

		}));

		links.add(loginLink);

		return links;
	}

	@Override
	public String toString() {
		return "LoginPointerLinkSystemEntry [loginLinkName=" + this.loginLinkName + ", loginTarget=" + this.loginTarget
		        + ", modelType=" + this.modelType + ", readService=" + this.readService + ", updater=" + this.updater
		        + ", reviewer=" + this.reviewer + ", validator=" + this.validator + ", deleteService="
		        + this.deleteService + ", deleteChangesMap=" + this.deleteChangesMap + "]";
	}

}
