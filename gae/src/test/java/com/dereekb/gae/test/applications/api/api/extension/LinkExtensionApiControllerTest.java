package com.dereekb.gae.test.applications.api.api.extension;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.web.api.model.extension.link.LinkExtensionApiController;

public class LinkExtensionApiControllerTest extends ApiApplicationTestContext {

	@Autowired
	private LinkExtensionApiController controller;

	public LinkExtensionApiController getController() {
		return this.controller;
	}

	public void setController(LinkExtensionApiController controller) {
		this.controller = controller;
	}

	@Test
	public void testControllerBean() {
		Assert.assertNotNull("Controller was not null.", this.controller);
		Assert.assertNotNull("Controller's Converter was not null.", this.controller.getConverter());
		Assert.assertNotNull("Controller's Service was not null.", this.controller.getService());
	}

}
