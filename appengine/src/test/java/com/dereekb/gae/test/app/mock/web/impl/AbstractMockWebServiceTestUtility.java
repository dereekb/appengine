package com.dereekb.gae.test.app.mock.web.impl;

import com.dereekb.gae.test.app.mock.web.MockWebServiceTestUtility;
import com.dereekb.gae.test.app.mock.web.WebServiceTester;

/**
 * Abstract {@link MockWebServiceTestUtility}.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractMockWebServiceTestUtility
        implements MockWebServiceTestUtility {

	private WebServiceTester webServiceTester;

	public AbstractMockWebServiceTestUtility() {}

	public AbstractMockWebServiceTestUtility(WebServiceTester webServiceTester) {
		this.setWebServiceTester(webServiceTester);
	}

	@Override
	public WebServiceTester getWebServiceTester() {
		if (this.webServiceTester == null) {
			throw new RuntimeException("WebServiceTester was not set yet.");
		}

		return this.webServiceTester;
	}

	@Override
	public void setWebServiceTester(WebServiceTester webServiceTester) {
		if (webServiceTester == null) {
			throw new IllegalArgumentException("WebServiceTester cannot be null.");
		}

		this.webServiceTester = webServiceTester;
	}

}
