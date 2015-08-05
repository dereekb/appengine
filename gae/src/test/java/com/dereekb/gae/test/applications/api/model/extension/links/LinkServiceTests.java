package com.dereekb.gae.test.applications.api.model.extension.links;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;

/**
 * Tests the configured {@link LinkService}.
 *
 * @author dereekb
 *
 */
public class LinkServiceTests extends ApiApplicationTestContext {

	private LinkService service;

	@Autowired
	public void setService(LinkService service) {
		this.service = service;
	}

	@Test
	public void testService() {

	}

}
