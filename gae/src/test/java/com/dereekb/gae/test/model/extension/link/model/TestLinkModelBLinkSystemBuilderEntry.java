package com.dereekb.gae.test.model.extension.link.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.system.components.SimpleLinkInfo;
import com.dereekb.gae.model.extension.links.system.components.impl.SimpleLinkInfoImpl;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.impl.AbstractMutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.MultipleMutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.MultipleMutableLinkDataDelegate;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.SingleMutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.SingleMutableLinkDataDelegate;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

public class TestLinkModelBLinkSystemBuilderEntry extends AbstractMutableLinkSystemBuilderEntry<TestLinkModelB> {

	public static final String LINK_MODEL_TYPE = TestLinkModelB.MODEL_ENTITY_NAME;

	public static final String PARENTS_LINK_NAME = "parents"; 
	public static final String MAIN_LINK_NAME = "main"; 
	
	public TestLinkModelBLinkSystemBuilderEntry(ReadService<TestLinkModelB> readService) {
		super(readService);
	}

	public TestLinkModelBLinkSystemBuilderEntry(ReadService<TestLinkModelB> readService,
	        Updater<TestLinkModelB> updater,
	        TaskRequestSender<TestLinkModelB> reviewTaskSender) {
		super(readService, updater, reviewTaskSender);
	}

	private SimpleLinkInfo parentLinkInfo = new SimpleLinkInfoImpl(PARENTS_LINK_NAME, TestLinkModelALinkSystemBuilderEntry.LINK_MODEL_TYPE);
	private SimpleLinkInfo mainLinkInfo = new SimpleLinkInfoImpl(MAIN_LINK_NAME, TestLinkModelALinkSystemBuilderEntry.LINK_MODEL_TYPE);

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
	protected List<MutableLinkData<TestLinkModelB>> makeLinkData() {
		List<MutableLinkData<TestLinkModelB>> linkData = new ArrayList<MutableLinkData<TestLinkModelB>>();

		// Parents
		MultipleMutableLinkData<TestLinkModelB> parentsLinkData = new MultipleMutableLinkData<TestLinkModelB>(this.parentLinkInfo, new MultipleMutableLinkDataDelegate<TestLinkModelB>() {

			@Override
			public Set<ModelKey> readLinkedModelKeys(TestLinkModelB model) {
				return model.getParentKeys();
			}

			@Override
			public void setLinkedModelKeys(TestLinkModelB model,
			                               Set<ModelKey> keys) {
				model.setParentKeys(keys);
			}
			
		});
		
		linkData.add(parentsLinkData);

		// Main
		SingleMutableLinkData<TestLinkModelB> mainLinkData = new SingleMutableLinkData<TestLinkModelB>(this.mainLinkInfo, new SingleMutableLinkDataDelegate<TestLinkModelB>() {

			@Override
			public ModelKey readLinkedModelKey(TestLinkModelB model) {
				return model.getMainKey();
			}

			@Override
			public void setLinkedModelKey(TestLinkModelB model,
			                              ModelKey modelKey) {
				model.setMainKey(modelKey);
			}
			
		});
		
		linkData.add(mainLinkData);
		
		return linkData;
	}

}
