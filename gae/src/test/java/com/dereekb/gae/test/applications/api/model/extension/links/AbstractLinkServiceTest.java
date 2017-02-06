package com.dereekb.gae.test.applications.api.model.extension.links;

import java.util.Collection;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.impl.RelationImpl;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.impl.bidirectional.BidirectionalLinkSystem;
import com.dereekb.gae.model.extension.links.deleter.LinkDeleterService;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.utilities.collections.SingleItem;

public abstract class AbstractLinkServiceTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("linkService")
	protected LinkService linkService;

	@Autowired
	@Qualifier("linkSystem")
	protected BidirectionalLinkSystem linkSystem;

	@Autowired
	@Qualifier("linkDeleterService")
	protected LinkDeleterService linkDeleterService;

	public LinkService getLinkService() {
		return this.linkService;
	}

	public void setLinkService(LinkService linkService) {
		this.linkService = linkService;
	}

	public BidirectionalLinkSystem getLinkSystem() {
		return this.linkSystem;
	}

	public void setLinkSystem(BidirectionalLinkSystem linkSystem) {
		this.linkSystem = linkSystem;
	}

	protected void linkModels(String linkType,
	                          UniqueModel primaryModel,
	                          String linkName,
	                          UniqueModel targetModel) {
		this.linkModels(linkType, primaryModel, linkName, SingleItem.withValue(targetModel));
	}

	protected void linkModels(String linkType,
	                          UniqueModel primaryModel,
	                          String linkName,
	                          Collection<? extends UniqueModel> targetModels) {
		LinkModelSet linkSet = this.linkSystem.loadSet(linkType);
		linkSet.loadModel(primaryModel.getModelKey());
		LinkModel model = linkSet.getModelForKey(primaryModel.getModelKey());

		Link newLink = model.getLink(linkName);

		Assert.assertNotNull(newLink);

		RelationImpl setBlob = new RelationImpl(ModelKey.readModelKeys(targetModels));
		newLink.addRelation(setBlob);

		linkSet.save(true);
	}

	protected void unlinkModels(String linkType,
	                            UniqueModel primaryModel,
	                            String linkName,
	                            UniqueModel targetModel) {
		this.unlinkModels(linkType, primaryModel, linkName, SingleItem.withValue(targetModel));
	}

	protected void unlinkModels(String linkType,
	                          UniqueModel primaryModel,
	                          String linkName,
	                          Collection<? extends UniqueModel> targetModels) {
		LinkModelSet linkSet = this.linkSystem.loadSet(linkType);
		linkSet.loadModel(primaryModel.getModelKey());
		LinkModel model = linkSet.getModelForKey(primaryModel.getModelKey());

		Link newLink = model.getLink(linkName);

		Assert.assertNotNull(newLink);

		RelationImpl setBlob = new RelationImpl(ModelKey.readModelKeys(targetModels));
		newLink.removeRelation(setBlob);

		linkSet.save(true);
	}

}
