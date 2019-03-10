package com.dereekb.gae.test.app.mock.web;

import com.dereekb.gae.test.spring.WebServiceTester;

/**
 * Test utility that relies on a {@link WebServiceTester}.
 * 
 * @author dereekb
 *
 */
public interface MockWebServiceTestUtility {

	public WebServiceTester getWebServiceTester();

	public void setWebServiceTester(WebServiceTester webServiceTester);

}
