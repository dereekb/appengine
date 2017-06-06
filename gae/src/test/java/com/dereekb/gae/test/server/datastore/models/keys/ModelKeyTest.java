package com.dereekb.gae.test.server.datastore.models.keys;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.utilities.collections.list.ListUtility;

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

	@Test
	public void testModelKeyEquality() {
		ModelKey string = new ModelKey("0");
		ModelKey number = new ModelKey(0);

		Assert.assertTrue(string.equals(number));
	}

	@Test
	public void testModelKeysInSet() {
		ModelKey string = new ModelKey("0");
		ModelKey number = new ModelKey(0);

		Set<ModelKey> sets = new HashSet<ModelKey>();
		sets.add(number);
		sets.add(string);

		Assert.assertTrue(sets.contains(string));
		Assert.assertTrue(sets.contains(number));
		Assert.assertTrue(sets.size() == 1);
	}

	@Test
	public void testModelKeyConversion() {
		ModelKey string = new ModelKey("0");
		ModelKey number = ModelKey.convert("0");

		Assert.assertTrue(string.equals(number));
		Assert.assertTrue(number.getType().equals(ModelKeyType.NUMBER));
	}

	@Test
	public void testModelKeyCompositeGeneration() {
		Long a = 100L;
		Long b = 200L;

		String name = ModelKey.makeCompositeName(a, b);
		Assert.assertNotNull(name);
		Assert.assertTrue(name.equals("100_200"));
	}

	@Test
	public void testModelKeyCompositeGenerationNullChecking() {
		Long a = 100L;
		Long b = null;

		try {
			ModelKey.makeCompositeName(a, b);
			Assert.fail("Name generation should have failed.");
		} catch (NullPointerException e) {

		}
	}

	@Test
	public void testModelKeyCompositeGenerationLengthChecking() {
		Long a = 100L;

		try {
			ModelKey.makeCompositeName(a);
			Assert.fail("Name generation should have failed.");
		} catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void testModelKeyCompositeGenerationConsistency() {
		Long a = 100L;
		Long b = 200L;

		ModelKey am = new ModelKey(a);
		ModelKey bm = new ModelKey(b);

		List<ModelKey> aa = ListUtility.wrap(am);
		List<ModelKey> ba = ListUtility.wrap(bm);

		String name = ModelKey.makeCompositeName(a, b);
		Assert.assertNotNull(name);
		Assert.assertTrue(name.equals("100_200"));

		List<ModelKey> aabmkeys = ModelKey.makeCompositeKeys(aa, bm);
		String aabmname = aabmkeys.get(0).getName();
		Assert.assertTrue(aabmname.equals(name));

		List<ModelKey> baamkeys = ModelKey.makeCompositeKeys(am, ba);
		String baamname = baamkeys.get(0).getName();
		Assert.assertTrue(baamname.equals(name));
	}

}
