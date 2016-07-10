package com.dereekb.gae.server.auth.security.login.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.exception.LinkException;
import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.service.impl.LinkServiceRequestImpl;
import com.dereekb.gae.model.extension.links.service.impl.LinkSystemChangeImpl;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.link.LoginLinkSystemEntry;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.link.LoginPointerLinkSystemEntry;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.NewLoginGenerator;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


/**
 * {@link LoginRegisterService} implementation.
 *
 * @author dereekb
 *
 */
public class LoginRegisterServiceImpl
        implements LoginRegisterService {

	private String loginLinkType = LoginLinkSystemEntry.LOGIN_LINK_TYPE;
	private String loginPointerLinkName = LoginPointerLinkSystemEntry.LOGIN_POINTER_LINK_TYPE;

	private NewLoginGenerator loginGenerator;
	private LinkService linkService;

	@Override
	public Login register(LoginPointer pointer) throws LoginExistsException {

		if (pointer == null) {
			throw new IllegalArgumentException("Pointer is null.");
		}

		if (pointer.getLogin() != null) {
			throw new LoginExistsException();
		}

		Login login = this.loginGenerator.makeLogin(pointer);
		Set<String> loginPointers = new HashSet<String>(1);
		loginPointers.add(pointer.getIdentifier());

		this.registerLogins(login.getModelKey(), loginPointers);
		return login;
	}

	@Override
	public void registerLogins(ModelKey loginKey,
	                           Set<String> loginPointers) throws LinkException {
		List<LinkSystemChange> changes = new ArrayList<LinkSystemChange>();

		LinkSystemChangeImpl change = new LinkSystemChangeImpl(LinkChangeAction.LINK, this.loginLinkType, loginKey,
		        this.loginPointerLinkName, loginPointers);
		changes.add(change);

		LinkServiceRequestImpl request = new LinkServiceRequestImpl(changes);
		this.linkService.updateLinks(request);
	}

}
