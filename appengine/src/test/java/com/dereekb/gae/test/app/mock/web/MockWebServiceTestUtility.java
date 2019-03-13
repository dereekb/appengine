package com.dereekb.gae.test.app.mock.web;

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
