package com.dereekb.gae.test.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.collections.batch.BatchGenerator;

public class BatchGeneratorTest {

	private static final Random random = new Random();

	@Test
	public void testEvenBatchGenerator() {

		Integer batchSize = 10;
		Integer itemsCount = 100;

		BatchGenerator<Integer> batchGenerator = new BatchGenerator<Integer>();
		List<Integer> items = this.generateRandomList(itemsCount);

		batchGenerator.setBatchSize(batchSize);
		List<List<Integer>> batches = batchGenerator.createBatchesWithCollection(items);

		Assert.assertTrue(batches.size() == (itemsCount / batchSize));

		for (List<Integer> batch : batches) {
			Assert.assertTrue(batch.size() == batchSize);
		}
	}

	@Test
	public void testOddBatchGenerator() {

		Integer batchSize = 9;
		Integer itemsCount = 100;

		BatchGenerator<Integer> batchGenerator = new BatchGenerator<Integer>();
		List<Integer> items = this.generateRandomList(itemsCount);

		batchGenerator.setBatchSize(batchSize);
		List<List<Integer>> batches = batchGenerator.createBatchesWithCollection(items);

		Assert.assertTrue(batches.size() == 12); // 100 / 9 = (11 + 1/9)

		for (List<Integer> batch : batches) {
			Assert.assertTrue(batch.size() <= batchSize);
		}

	}

	@Test
	public void nullCollectionBatchGenerator() {

		BatchGenerator<Integer> batchGenerator = new BatchGenerator<Integer>();
		List<Integer> items = null;

		List<List<Integer>> batches = batchGenerator.createBatchesWithCollection(items);

		Assert.assertNotNull(batches);
		Assert.assertTrue(batches.size() == 0);
	}

	private List<Integer> generateRandomList(Integer count) {

		List<Integer> items = new ArrayList<Integer>(count);

		for (Integer i = 0; i < count; i += 1) {
			Integer item = random.nextInt();
			items.add(item);
		}

		return items;
	}

}
