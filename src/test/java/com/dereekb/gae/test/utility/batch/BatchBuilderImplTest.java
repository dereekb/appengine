package com.dereekb.gae.test.utility.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.collections.batch.Batch;
import com.dereekb.gae.utilities.collections.batch.BatchBuilder;
import com.dereekb.gae.utilities.collections.batch.Partition;
import com.dereekb.gae.utilities.collections.batch.impl.BatchBuilderImpl;

/**
 * {@link BatchImpl} tests.
 *
 * @author dereekb
 *
 */
public class BatchBuilderImplTest {

	private static final Random random = new Random();

	@Test
	public void testEvenBatchGenerator() {

		Integer batchSize = 10;
		Integer itemsCount = 100;

		BatchBuilder batchBuilder = new BatchBuilderImpl(batchSize);
		List<Integer> items = this.generateRandomList(itemsCount);

		Batch<Integer> batches = batchBuilder.makeBatchWithCollection(items);

		Assert.assertTrue(batches.getTotalSize() == itemsCount);
		Assert.assertTrue(batches.getPartitionCount() == (itemsCount / batchSize));

		for (Partition<Integer> partition : batches) {
			Assert.assertTrue(partition.getPartitionSize() == batchSize);
		}
	}

	@Test
	public void testOddBatchGenerator() {

		Integer batchSize = 9;
		Integer itemsCount = 100;

		BatchBuilder batchBuilder = new BatchBuilderImpl(batchSize);
		List<Integer> items = this.generateRandomList(itemsCount);

		Batch<Integer> batches = batchBuilder.makeBatchWithCollection(items);

		Assert.assertTrue(batches.getTotalSize() == itemsCount);
		Assert.assertTrue(batches.getPartitionCount() == 12); // 100 / 9 = (11 +
															  // 1/9)
	}

	/*
	@Test
	public void nullCollectionBatchGenerator() {

		BatchGenerator<Integer> batchGenerator = new BatchGenerator<Integer>();
		List<Integer> items = null;

		List<List<Integer>> batches = batchGenerator.createBatchesWithCollection(items);

		Assert.assertNotNull(batches);
		Assert.assertTrue(batches.size() == 0);
	}
	*/

	private List<Integer> generateRandomList(Integer count) {

		List<Integer> items = new ArrayList<Integer>(count);

		for (Integer i = 0; i < count; i += 1) {
			Integer item = random.nextInt();
			items.add(item);
		}

		return items;
	}

}
