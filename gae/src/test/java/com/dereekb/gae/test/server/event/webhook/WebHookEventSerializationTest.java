package com.dereekb.gae.test.server.event.webhook;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.model.shared.event.impl.CommonModelEventType;
import com.dereekb.gae.server.event.webhook.impl.JsonWebHookEventImpl;
import com.dereekb.gae.server.event.webhook.impl.WebHookEventImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebHookEventSerializationTest {

	/**
	 * Test serializing a {@link WebHookEventImpl} to JSON, then to a
	 * {@link JsonWebHookEventImpl}.
	 *
	 * @throws IOException
	 */
	@Test
	public void testSerializingBasicWebHookEvent() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		EventType eventType = CommonModelEventType.CREATED;
		List<String> testData = ListUtility.toList("a", "b", "c");

		WebHookEventImpl event = new WebHookEventImpl(eventType, testData);

		// Convert to JSON
		JsonNode node = event.getJsonNode(); // mapper.valueToTree(event);

		String json = node.toString();

		JsonNode readNode = mapper.readTree(json);

		JsonWebHookEventImpl jsonWebHook = new JsonWebHookEventImpl(readNode);
		Assert.assertTrue(EventType.isEquivalent(eventType, jsonWebHook.getEventType()));
	}

	// TODO: Test serializing event with missing group/type throws an exception.

}
