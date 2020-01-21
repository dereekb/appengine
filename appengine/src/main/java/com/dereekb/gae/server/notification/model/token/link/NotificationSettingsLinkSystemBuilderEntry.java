package com.dereekb.gae.server.notification.model.token.link;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.impl.AbstractMutableLinkSystemBuilderEntry;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.notification.model.token.NotificationSettings;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link MutableLinkSystemBuilderEntry} implementation for
 * {@link NotificationSettings}.
 *
 * @author dereekb
 *
 */
public class NotificationSettingsLinkSystemBuilderEntry extends AbstractMutableLinkSystemBuilderEntry<NotificationSettings> {

	public static final String LINK_MODEL_TYPE = "NotificationSettings";

	public NotificationSettingsLinkSystemBuilderEntry(ReadService<NotificationSettings> readService,
	        Updater<NotificationSettings> updater,
	        TaskRequestSender<NotificationSettings> reviewTaskSender) {
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
	protected List<MutableLinkData<NotificationSettings>> makeLinkData() {
		return Collections.emptyList();
	}

}
