package com.dereekb.gae.test.app.mock.client.extension;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.extension.search.query.builder.ClientQueryRequestSender;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.extension.search.query.service.ModelQueryResponse;
import com.dereekb.gae.model.extension.search.query.service.impl.ModelQueryRequestImpl;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.model.search.exception.KeysOnlySearchException;
import com.dereekb.gae.utilities.model.search.request.MutableSearchRequest;

/**
 * Test utility for {@link ClientQueryRequestSender}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelClientQueryRequestSenderTestUtility<T extends MutableUniqueModel> {

	private final TestModelGenerator<T> testModelGenerator;
	private final ClientQueryRequestSender<T> queryRequestSender;

	public ModelClientQueryRequestSenderTestUtility(ClientQueryRequestSender<T> queryRequestSender,
	        TestModelGenerator<T> testModelGenerator) {
		this.queryRequestSender = queryRequestSender;
		this.testModelGenerator = testModelGenerator;
	}

	// MARK: Tests
	public void testMockQueryRequest(ClientRequestSecurity security) throws ClientRequestFailureException {

		int limit = 5;
		int total = limit * 2;
		this.testModelGenerator.generate(total);

		MutableSearchRequest queryRequest = new ModelQueryRequestImpl();
		queryRequest.setLimit(limit);
		queryRequest.setKeysOnly(false);	// Return Models

		ModelQueryResponse<T> firstResponse = this.queryRequestSender.query(queryRequest, security);
		Collection<T> firstResultModels = null;

		try {
			firstResultModels = firstResponse.getModelResults();
		} catch (KeysOnlySearchException e) {
			Assert.fail("Should be a model search.");
		}

		Assert.assertTrue(firstResultModels.size() == limit);

		Collection<ModelKey> firstResultKeys = firstResponse.getKeyResults();
		Assert.assertFalse(firstResultKeys.isEmpty());

		ResultsCursor searchCursor = firstResponse.getSearchCursor();
		Assert.assertNotNull(searchCursor);

		// Set the search cursor
		queryRequest.setCursor(searchCursor);

		ModelQueryResponse<T> secondResponse = this.queryRequestSender.query(queryRequest, security);
		Collection<ModelKey> secondResultKeys = secondResponse.getKeyResults();

		Set<ModelKey> secondResultKeySet = new HashSet<ModelKey>(secondResultKeys);

		// Should not contain any of the first results.
		Assert.assertFalse(secondResultKeySet.contains(firstResultKeys.iterator().next()));
	}

	public void testMockQueryRequestKeysOnly(ClientRequestSecurity security) throws ClientRequestFailureException {

		int limit = 5;
		int total = limit * 2;
		List<T> models = this.testModelGenerator.generate(total);

		MutableSearchRequest queryRequest = new ModelQueryRequestImpl();
		queryRequest.setLimit(limit);
		queryRequest.setKeysOnly(true);	// Return Models

		ModelQueryResponse<T> firstResponse = this.queryRequestSender.query(queryRequest, security);

		try {
			firstResponse.getModelResults();
			Assert.fail("Should be a keys only search.");
		} catch (KeysOnlySearchException e) {

		}

		Collection<ModelKey> firstResultKeys = firstResponse.getKeyResults();
		Assert.assertTrue(firstResultKeys.size() == limit);

		ResultsCursor searchCursor = firstResponse.getSearchCursor();
		Assert.assertNotNull(searchCursor);

		Assert.assertFalse(models.isEmpty());
	}

}
