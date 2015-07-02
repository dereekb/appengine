package com.dereekb.gae.test.server.datastore.models.keys;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Tests for {@link ModelKey}.
 *
 * @author dereekb
 *
 */
public class ModelKeyTest {

	@Test
	public void testModelKeyHashing() {
		ModelKey keyA = new ModelKey(1L);
		ModelKey keyB = new ModelKey(2L);
		ModelKey keyC = new ModelKey(1L);

		int keyAhash = keyA.hashCode();
		int keyBhash = keyB.hashCode();
		int keyChash = keyC.hashCode();

		Assert.assertTrue(keyAhash == keyChash);
		Assert.assertTrue(keyAhash != keyBhash);
		Assert.assertTrue(keyBhash != keyChash);
	}

	@Test
	public void testModelStringHashing() {

		Map<ModelKey, String> keysMap = new HashMap<ModelKey, String>();
		keysMap.put(new ModelKey(1L), "A");
		keysMap.put(new ModelKey(2L), "B");
		keysMap.put(new ModelKey(3L), "C");

		String a = keysMap.get(new ModelKey(1L));
		Assert.assertNotNull(a);
		Assert.assertTrue(a.equals("A"));

	}

}
