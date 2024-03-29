package com.dereekb.gae.test.utility.data;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.auth.model.login.dto.LoginData;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.data.ObjectMapperUtility;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.fasterxml.jackson.databind.JsonNode;

public class ObjectMapperUtilityTest {

	@Test
	public void testSerializeList() throws IOException {
		ObjectMapperUtility utility = ObjectMapperUtilityBuilderImpl.SINGLETON.make();

		List<String> testData = ListUtility.toList("a", "b", "c");

		JsonNode node = utility.getMapper().valueToTree(testData);

		List<String> serializedTestData = utility.mapArrayToList(node, String.class);
		assertTrue(testData.containsAll(serializedTestData));
		assertTrue(serializedTestData.containsAll(testData));
	}

	@Test
	public void testSerializeToIncompatableType() throws IOException {
		ObjectMapperUtility utility = ObjectMapperUtilityBuilderImpl.SINGLETON.make();

		List<String> testData = ListUtility.toList("a", "b", "c");

		JsonNode node = utility.getMapper().valueToTree(testData);

		String jsonString = node.toString();

		node = utility.getMapper().readTree(jsonString);

		try {
			utility.mapArrayToList(node, LoginData.class);
			fail();
		} catch (IOException e) {

		}
	}

}
