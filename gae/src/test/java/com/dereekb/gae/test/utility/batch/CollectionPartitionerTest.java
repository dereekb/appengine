package com.dereekb.gae.test.utility.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.collections.batch.CollectionPartitioner;

/**
 * Unit tests for {@link CollectionPartitioner}.
 * 
 * @author dereekb
 *
 */
public class CollectionPartitionerTest {

	private static final Random random = new Random();

	@Test
	public void testEvenPartitionGenerator() {

		Integer partitionSize = 10;
		Integer itemsCount = 100;

		CollectionPartitioner partitioner = new CollectionPartitioner();
		List<Integer> items = this.generateRandomList(itemsCount);

		partitioner.setPartitionSize(partitionSize);
		List<List<Integer>> partitions = partitioner.partitionsWithCollection(items);

		Assert.assertTrue(partitions.size() == (itemsCount / partitionSize));

		for (List<Integer> partition : partitions) {
			Assert.assertTrue(partition.size() == partitionSize);
		}
	}

	@Test
	public void testOddPartitionGenerator() {

		Integer partitionSize = 9;
		Integer itemsCount = 100;

		CollectionPartitioner partitioner = new CollectionPartitioner();
		List<Integer> items = this.generateRandomList(itemsCount);

		partitioner.setPartitionSize(partitionSize);
		List<List<Integer>> partitions = partitioner.partitions(items);

		Assert.assertTrue(partitions.size() == 12); // 100 / 9 = (11 + 1/9)

		for (List<Integer> partition : partitions) {
			Assert.assertTrue(partition.size() <= partitionSize);
		}

	}

	@Test
	public void testNullCollectionPartitionGenerator() {

		CollectionPartitioner partitioner = new CollectionPartitioner();
		List<Integer> items = null;

		List<List<Integer>> partitions = partitioner.partitionsWithCollection(items);

		Assert.assertNotNull(partitions);
		Assert.assertTrue(partitions.size() == 0);
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
