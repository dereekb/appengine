package com.dereekb.gae.test.applications.api.api.tests.client;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.client.api.model.extension.search.query.builder.ClientQueryRequestSender;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.test.app.web.api.model.AbstractServerModelRequestSenderTest;
import com.dereekb.gae.test.applications.api.ClientApiApplicationTestContext;
import com.dereekb.gae.test.mock.client.extension.ModelClientQueryRequestSenderTestUtility;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

/**
 * Abstract testing class used for testing Search requests on a model type.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 *
 * @deprecated replaced by {@link AbstractServerModelRequestSenderTest}.
 */
@Deprecated
public abstract class ClientApiSearchTest<T extends MutableUniqueModel> extends ClientApiApplicationTestContext {

	private TestModelGenerator<T> testModelGenerator;

	private ClientQueryRequestSender<T> queryRequestSender;

	private ModelClientQueryRequestSenderTestUtility<T> queryRequestUtility;

	public TestModelGenerator<T> getTestModelGenerator() {
		return this.testModelGenerator;
	}

	public void setTestModelGenerator(TestModelGenerator<T> testModelGenerator) {
		this.testModelGenerator = testModelGenerator;
	}

	public ClientQueryRequestSender<T> getQueryRequestSender() {
		return this.queryRequestSender;
	}

	public void setQueryRequestSender(ClientQueryRequestSender<T> queryRequestSender) {
		this.queryRequestSender = queryRequestSender;
	}

	// MARK: Utilities
	@BeforeEach
	public void setUtilities() {
		if (this.queryRequestSender != null) {
			this.queryRequestUtility = new ModelClientQueryRequestSenderTestUtility<T>(this.queryRequestSender,
			        this.testModelGenerator);
		}
	}

	public ModelClientQueryRequestSenderTestUtility<T> getQueryRequestUtility() {
		return this.queryRequestUtility;
	}

	// MARK: Query Tests
	@Test
	public void testSystemClientQuery() throws Exception {
		if (this.queryRequestUtility != null) {
			this.queryRequestUtility.testMockQueryRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientKeysOnlyQuery() throws Exception {
		if (this.queryRequestUtility != null) {
			this.queryRequestUtility.testMockQueryRequestKeysOnly(this.getRequestSecurity());
		}
	}

}
