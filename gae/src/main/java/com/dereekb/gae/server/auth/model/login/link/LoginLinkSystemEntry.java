package com.dereekb.gae.server.auth.model.login.link;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.impl.LinkTargetImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.DescribedModelLinkInfo;
import com.dereekb.gae.model.extension.links.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.impl.AbstractDescriptiveModelLinkSystemEntry;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.link.LoginPointerLinkSystemEntry;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;

/**
 * {@link LinkSystemEntry} implementation for {@link Login}.
 *
 * @author dereekb
 *
 */
@Deprecated
public class LoginLinkSystemEntry extends AbstractDescriptiveModelLinkSystemEntry<Login> {

	public static final String LOGIN_LINK_TYPE = "Login";

	private static final ExtendedObjectifyModelKeyUtil<LoginPointer> loginPointerUtil = ExtendedObjectifyModelKeyUtil
	        .make(LoginPointer.class, ModelKeyType.NAME);

	private String loginPointersLinkName = LoginPointerLinkSystemEntry.LOGIN_POINTER_LINK_TYPE;

	private LinkTarget loginPointerTarget = new LinkTargetImpl(LoginPointerLinkSystemEntry.LOGIN_POINTER_LINK_TYPE,
	        ModelKeyType.NAME);

	public LoginLinkSystemEntry(CrudService<Login> crudService, Updater<Login> updater) {
		super(LOGIN_LINK_TYPE, crudService, crudService, updater);
	}

	public LoginLinkSystemEntry(CrudService<Login> crudService,
	        Updater<Login> updater,
	        List<DescribedModelLinkInfo> info) {
		super(LOGIN_LINK_TYPE, crudService, crudService, updater, info);
	}

	public LoginLinkSystemEntry(ReadService<Login> readService,
	        DeleteService<Login> deleteService,
	        Updater<Login> updater) {
		super(LOGIN_LINK_TYPE, readService, deleteService, updater);
	}

	public LoginLinkSystemEntry(ReadService<Login> readService,
	        DeleteService<Login> deleteService,
	        Updater<Login> updater,
	        List<DescribedModelLinkInfo> info) {
		super(LOGIN_LINK_TYPE, readService, deleteService, updater, info);
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
	public List<Link> makeDefinedLinksForModel(final Login model) {
		List<Link> links = new ArrayList<Link>();

		// No Links.

		return links;
	}

	@Override
	public String toString() {
		return "LoginLinkSystemEntry [loginPointersLinkName=" + this.loginPointersLinkName + ", loginPointerTarget="
		        + this.loginPointerTarget + ", modelType=" + this.modelType + ", readService=" + this.readService
		        + ", updater=" + this.updater + ", reviewer=" + this.reviewer + ", validator=" + this.validator
		        + ", deleteService=" + this.deleteService + ", deleteChangesMap=" + this.deleteChangesMap + "]";
	}

}
