package com.dereekb.gae.server.auth.model.login.link;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.impl.AbstractMutableLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link MutableLinkSystemBuilderEntry} implementation for {@link Login}.
 *
 * @author dereekb
 *
 */
public class LoginLinkSystemBuilderEntry extends AbstractMutableLinkSystemBuilderEntry<Login> {

	public static final String LOGIN_LINK_TYPE = "Login";

	public LoginLinkSystemBuilderEntry(ReadService<Login> readService,
	        Updater<Login> updater,
	        TaskRequestSender<Login> reviewTaskSender) {
		super(readService, updater, reviewTaskSender);
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
