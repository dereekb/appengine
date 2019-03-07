package com.dereekb.gae.extras.gen.test.model.foo.link;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.impl.AbstractMutableLinkSystemBuilderEntry;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link MutableLinkSystemBuilderEntry} implementation for {@link Foo}.
 *
 * @author dereekb
 *
 */
public class FooLinkSystemBuilderEntry extends AbstractMutableLinkSystemBuilderEntry<Foo> {

	public static final String LINK_MODEL_TYPE = "Foo";

	public FooLinkSystemBuilderEntry(ReadService<Foo> readService,
	        Updater<Foo> updater,
	        TaskRequestSender<Foo> reviewTaskSender) {
		super(readService, updater, reviewTaskSender);
	}

	// MARK: AbstractMutableLinkSystemBuilderEntry
	@Override
	public String getLinkModelType() {
		return LINK_MODEL_TYPE;
	}

	@Override
	public ModelKeyType getModelKeyType() {
		return ModelKeyType.NAME;
	}

	@Override
	protected List<MutableLinkData<Foo>> makeLinkData() {
		List<MutableLinkData<Foo>> linkData = new ArrayList<MutableLinkData<Foo>>();

		// TODO

		return linkData;
	}

}
