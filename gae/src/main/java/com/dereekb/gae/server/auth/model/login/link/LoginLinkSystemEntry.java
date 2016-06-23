package com.dereekb.gae.server.auth.model.login.link;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.impl.LinkInfoImpl;
import com.dereekb.gae.model.extension.links.components.impl.LinkTargetImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.LinkCollectionImpl;
import com.dereekb.gae.model.extension.links.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.impl.AbstractModelLinkSystemEntry;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.link.LoginPointerLinkSystemEntry;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.googlecode.objectify.Key;

/**
 * {@link LinkSystemEntry} implementation for {@link Login}.
 *
 * @author dereekb
 *
 */
public class LoginLinkSystemEntry extends AbstractModelLinkSystemEntry<Login> {

	public static final String LOGIN_LINK_TYPE = "Login";

	private static final ExtendedObjectifyModelKeyUtil<LoginPointer> loginPointerUtil = ExtendedObjectifyModelKeyUtil.make(
	        LoginPointer.class, ModelKeyType.NUMBER);

	private String loginPointersLinkName = LoginPointerLinkSystemEntry.LOGIN_POINTER_LINK_TYPE;

	private LinkTarget loginPointerTarget = new LinkTargetImpl(LoginPointerLinkSystemEntry.LOGIN_POINTER_LINK_TYPE,
	        ModelKeyType.NAME);

	public LoginLinkSystemEntry(CrudService<Login> crudService,
	                            ConfiguredSetter<Login> setter) {
		super(LOGIN_LINK_TYPE, crudService, crudService, setter);
	}

	public String getLoginPointersLinkName() {
		return this.loginPointersLinkName;
	}

	public void setLoginPointersLinkName(String loginPointersLinkName) {
		this.loginPointersLinkName = loginPointersLinkName;
	}

	public LinkTarget getLoginPointerTarget() {
		return this.loginPointerTarget;
	}

	public void setLoginPointerTarget(LinkTarget loginPointerTarget) {
		this.loginPointerTarget = loginPointerTarget;
	}

	public static ExtendedObjectifyModelKeyUtil<LoginPointer> getLoginpointerutil() {
		return loginPointerUtil;
	}

	// MARK: AbstractModelLinkSystemEntry
	@Override
	public List<Link> getLinks(final Login model) {
		List<Link> links = new ArrayList<Link>();

		ModelKey key = model.getModelKey();

		// Pointers Link
		LinkInfoImpl loginPointersLinkInfo = new LinkInfoImpl(this.loginPointersLinkName, key, this.loginPointerTarget);
		LinkCollectionImpl<Key<LoginPointer>> imagesLink = new LinkCollectionImpl<Key<LoginPointer>>(
		        loginPointersLinkInfo,
		        model.getPointers(), loginPointerUtil);
		links.add(imagesLink);

		return links;
	}

	@Override
	public String toString() {
		return "LoginLinkSystemEntry [loginPointersLinkName=" + this.loginPointersLinkName + ", loginPointerTarget="
		        + this.loginPointerTarget + ", modelType=" + this.modelType + ", readService=" + this.readService
		        + ", setter=" + this.setter + ", reviewer=" + this.reviewer + ", validator=" + this.validator
		        + ", deleteService=" + this.deleteService + ", deleteChangesMap=" + this.deleteChangesMap + "]";
	}

}
