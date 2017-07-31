package com.dereekb.gae.server.auth.model.login.link;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.impl.AbstractMutableLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link MutableLinkSystemBuilderEntry} implementation for {@link Login}.
 *
 * @author dereekb
 *
 */
public class LoginLinkSystemBuilderEntry extends AbstractMutableLinkSystemBuilderEntry<Login> {

	public static final String LOGIN_LINK_TYPE = "Login";

	public LoginLinkSystemBuilderEntry(ReadService<Login> readService) {
		super(readService);
	}

	// MARK: AbstractModelLinkSystemEntry
	@Override
	public String getLinkModelType() {
		return LOGIN_LINK_TYPE;
	}

	@Override
	public ModelKeyType getModelKeyType() {
		return ModelKeyType.NUMBER;
	}

	@Override
	protected List<MutableLinkData<Login>> makeLinkData() {
		return Collections.emptyList();
	}

}
