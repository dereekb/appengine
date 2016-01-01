package com.dereekb.gae.test.applications.api.model.extension.links;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.links.components.system.impl.bidirectional.BidirectionalLinkSystem;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;

public class AbstractLinkServiceTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("linkService")
	protected LinkService linkService;

	@Autowired
	@Qualifier("linkSystem")
	protected BidirectionalLinkSystem linkSystem;

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

}
