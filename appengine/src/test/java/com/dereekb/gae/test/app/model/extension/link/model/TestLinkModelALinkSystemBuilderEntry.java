package com.dereekb.gae.test.app.model.extension.link.model;

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

public class TestLinkModelALinkSystemBuilderEntry extends AbstractMutableLinkSystemBuilderEntry<TestLinkModelA> {

	public static final String LINK_MODEL_TYPE = TestLinkModelA.MODEL_ENTITY_NAME;

	public static final String PRIMARY_LINK_NAME = "parent"; 
	public static final String A_CHILDREN_LINK_NAME = "aChildren"; 
	public static final String SECONDARY_LINK_NAME = "secondary"; 
	public static final String B_CHILDREN_LINK_NAME = "bChildren"; 
	public static final String THIRD_LINK_NAME = "third"; 
	
	public TestLinkModelALinkSystemBuilderEntry(ReadService<TestLinkModelA> readService) {
		super(readService);
	}
	
	public TestLinkModelALinkSystemBuilderEntry(ReadService<TestLinkModelA> readService,
	        Updater<TestLinkModelA> updater,
	        TaskRequestSender<TestLinkModelA> reviewTaskSender) {
		super(readService, updater, reviewTaskSender);
	}

	private SimpleLinkInfo primaryLinkInfo = new SimpleLinkInfoImpl(PRIMARY_LINK_NAME, LINK_MODEL_TYPE);
	private SimpleLinkInfo aChildrenLinkInfo = new SimpleLinkInfoImpl(A_CHILDREN_LINK_NAME, LINK_MODEL_TYPE);
	private SimpleLinkInfo secondaryLinkInfo = new SimpleLinkInfoImpl(SECONDARY_LINK_NAME, TestLinkModelBLinkSystemBuilderEntry.LINK_MODEL_TYPE);
	private SimpleLinkInfo bChildrenLinkInfo = new SimpleLinkInfoImpl(B_CHILDREN_LINK_NAME, TestLinkModelBLinkSystemBuilderEntry.LINK_MODEL_TYPE);
	private SimpleLinkInfo thirdLinkInfo = new SimpleLinkInfoImpl(THIRD_LINK_NAME, TestLinkModelBLinkSystemBuilderEntry.LINK_MODEL_TYPE);
	
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
	protected List<MutableLinkData<TestLinkModelA>> makeLinkData() {
		List<MutableLinkData<TestLinkModelA>> linkData = new ArrayList<MutableLinkData<TestLinkModelA>>();

		// Primary
		SingleMutableLinkData<TestLinkModelA> primaryLinkData = new SingleMutableLinkData<TestLinkModelA>(this.primaryLinkInfo, new SingleMutableLinkDataDelegate<TestLinkModelA>() {

			@Override
			public ModelKey readLinkedModelKey(TestLinkModelA model) {
				return model.getPrimaryKey();
			}

			@Override
			public void setLinkedModelKey(TestLinkModelA model,
			                              ModelKey modelKey) {
				model.setPrimaryKey(modelKey);
			}
			
		});
		
		linkData.add(primaryLinkData);

		
		// A Children
		MultipleMutableLinkData<TestLinkModelA> aChildrenLinkData = new MultipleMutableLinkData<TestLinkModelA>(this.aChildrenLinkInfo, new MultipleMutableLinkDataDelegate<TestLinkModelA>() {

			@Override
			public Set<ModelKey> readLinkedModelKeys(TestLinkModelA model) {
				return model.getaChildKeys();
			}

			@Override
			public void setLinkedModelKeys(TestLinkModelA model,
			                               Set<ModelKey> keys) {
				model.setaChildKeys(keys);
			}
			
		});

		linkData.add(aChildrenLinkData);
		
		// Secondary
		SingleMutableLinkData<TestLinkModelA> secondaryLinkData = new SingleMutableLinkData<TestLinkModelA>(this.secondaryLinkInfo, new SingleMutableLinkDataDelegate<TestLinkModelA>() {

			@Override
			public ModelKey readLinkedModelKey(TestLinkModelA model) {
				return model.getSecondaryKey();
			}

			@Override
			public void setLinkedModelKey(TestLinkModelA model,
			                              ModelKey modelKey) {
				model.setSecondaryKey(modelKey);
			}
			
		});
		
		linkData.add(secondaryLinkData);
		
		// B Children
		MultipleMutableLinkData<TestLinkModelA> bChildrenLinkData = new MultipleMutableLinkData<TestLinkModelA>(this.bChildrenLinkInfo, new MultipleMutableLinkDataDelegate<TestLinkModelA>() {

			@Override
			public Set<ModelKey> readLinkedModelKeys(TestLinkModelA model) {
				return model.getbChildKeys();
			}

			@Override
			public void setLinkedModelKeys(TestLinkModelA model,
			                               Set<ModelKey> keys) {
				model.setbChildKeys(keys);
			}
			
		});
		
		linkData.add(bChildrenLinkData);

		// Third
		SingleMutableLinkData<TestLinkModelA> thirdLinkData = new SingleMutableLinkData<TestLinkModelA>(this.thirdLinkInfo, new SingleMutableLinkDataDelegate<TestLinkModelA>() {

			@Override
			public ModelKey readLinkedModelKey(TestLinkModelA model) {
				return model.getThirdKey();
			}

			@Override
			public void setLinkedModelKey(TestLinkModelA model,
			                              ModelKey modelKey) {
				model.setThirdKey(modelKey);
			}
			
		});
		
		linkData.add(thirdLinkData);
		
		return linkData;
	}

}
