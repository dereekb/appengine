package com.dereekb.gae.test.applications.api.api.tests.client;

import org.junit.Before;
import org.junit.Test;

import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceRequestSender;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.test.applications.api.ClientApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.model.tests.AbstractServerModelRequestSenderTest;
import com.dereekb.gae.test.mock.client.extension.ModelClientLinkRequestSenderTestUtility;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

/**
 * Abstract testing class used for testing Link requests on a model type.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 *
 * @deprecated Tests merged into {@link AbstractServerModelRequestSenderTest}
 */
@Deprecated
public abstract class ClientApiLinkTest<T extends MutableUniqueModel> extends ClientApiApplicationTestContext {

	private TestModelGenerator<T> testModelGenerator;

	private ClientLinkServiceRequestSender linkRequestSender;

	private ModelClientLinkRequestSenderTestUtility<T> linkRequestUtility;

	public TestModelGenerator<T> getTestModelGenerator() {
		return this.testModelGenerator;
	}

	public void setTestModelGenerator(TestModelGenerator<T> testModelGenerator) {
		this.testModelGenerator = testModelGenerator;
	}

	public ClientLinkServiceRequestSender getLinkRequestSender() {
		return this.linkRequestSender;
	}

	public void setLinkRequestSender(ClientLinkServiceRequestSender linkRequestSender) {
		this.linkRequestSender = linkRequestSender;
	}

	public ModelClientLinkRequestSenderTestUtility<T> getLinkRequestUtility() {
		return this.linkRequestUtility;
	}

	// MARK: Utilities
	@Before
	public void setUtilities() {
		if (this.linkRequestSender != null) {
			this.linkRequestUtility = new ModelClientLinkRequestSenderTestUtility<T>(this.testModelGenerator,
			        this.linkRequestSender);
		}
	}

	// MARK: Tests
	@Test
	public void testSystemClientLinkToNonExistantLink() throws Exception {
		if (this.linkRequestUtility != null) {
			this.linkRequestUtility.testSystemClientLinkToNonExistantLink(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientLinkAtomicRequest() throws Exception {
		if (this.linkRequestUtility != null) {
			this.linkRequestUtility.testSystemClientLinkAtomicRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientLinkNonAtomicRequest() throws Exception {
		if (this.linkRequestUtility != null) {
			this.linkRequestUtility.testSystemClientLinkNonAtomicRequest(this.getRequestSecurity());
		}
	}

}
