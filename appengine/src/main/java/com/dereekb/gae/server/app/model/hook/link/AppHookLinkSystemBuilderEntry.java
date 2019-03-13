package com.dereekb.gae.server.app.model.hook.link;

import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderEntry;
import com.dereekb.gae.server.app.model.app.shared.link.AbstractAppRelatedModelLinkSystemBuilderEntry;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
/**
 * {@link MutableLinkSystemBuilderEntry} implementation for
 * {@link AppHook}.
 *
 * @author dereekb
 *
 */
public class AppHookLinkSystemBuilderEntry extends AbstractAppRelatedModelLinkSystemBuilderEntry<AppHook> {

	public static final String LINK_MODEL_TYPE = "AppHook";

	public AppHookLinkSystemBuilderEntry(ReadService<AppHook> readService,
	        Updater<AppHook> updater,
	        TaskRequestSender<AppHook> reviewTaskSender) {
		super(readService, updater, reviewTaskSender);
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
	protected List<MutableLinkData<AppHook>> makeLinkData() {
		List<MutableLinkData<AppHook>> linkData = super.makeLinkData();

		return linkData;
	}

	@Override
	public String toString() {
		return "AppHookLinkSystemBuilderEntry []";
	}

}
