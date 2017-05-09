package com.dereekb.gae.test.mock.client.extension;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceRequest;
import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceRequestSender;
import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceResponse;
import com.dereekb.gae.client.api.model.extension.link.impl.ClientApiLinkChange;
import com.dereekb.gae.client.api.model.extension.link.impl.ClientLinkServiceRequestImpl;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.extension.link.ApiLinkChange;

/**
 * Test utility for {@link ClientLinkServiceRequestSender}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelClientLinkRequestSenderTestUtility<T extends MutableUniqueModel> {

	private final TestModelGenerator<T> testModelGenerator;
	private final ClientLinkServiceRequestSender linkRequestSender;

	public ModelClientLinkRequestSenderTestUtility(ClientLinkServiceRequestSender linkRequestSender,
	        TestModelGenerator<T> testModelGenerator) {
		this.linkRequestSender = linkRequestSender;
		this.testModelGenerator = testModelGenerator;
	}

	// MARK: Tests
	public void testSystemClientLinkToNonExistantLink(ClientRequestSecurity security) throws Exception {
		String type = this.testModelGenerator.getTypeName();

		T model = this.testModelGenerator.generate();

		List<ApiLinkChange> changes = new ArrayList<>();

		ClientApiLinkChange linkChange = new ClientApiLinkChange();
		linkChange.setPrimary(model.getModelKey());
		linkChange.setChangeAction(LinkChangeAction.CLEAR);
		linkChange.setLinkName("NON_EXISTANT_LINK_NAME");

		changes.add(linkChange);

		ClientLinkServiceRequest request = new ClientLinkServiceRequestImpl(type, changes);

		SerializedClientApiResponse<ClientLinkServiceResponse> response = this.linkRequestSender.sendRequest(request,
		        security);

		Assert.assertFalse(response.getSuccess());

		try {
			ClientLinkServiceResponse serviceResponse = response.getSerializedPrimaryData();
			Assert.fail();
		} catch (Exception e) {

		}
	}

	public void testSystemClientLinkToUnavailableModel() throws Exception {

	}

	public void testSystemClientLinkAtomicRequest() throws Exception {

	}

	public void testSystemClientLinkNonAtomicRequest() throws Exception {

	}

}
