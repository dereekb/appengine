package com.dereekb.gae.test.utility.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;

/**
 * Unit tests for {@link PartitionerImpl}.
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

		PartitionerImpl partitioner = new PartitionerImpl();
		List<Integer> items = this.generateRandomList(itemsCount);

		partitioner.setDefaultPartitionSize(partitionSize);
		List<List<Integer>> partitions = partitioner.makePartitionsWithCollection(items);

		assertTrue(partitions.size() == (itemsCount / partitionSize));

		for (List<Integer> partition : partitions) {
			assertTrue(partition.size() == partitionSize);
		}
	}

	@Test
	public void testOddPartitionGenerator() {

		Integer partitionSize = 9;
		Integer itemsCount = 100;

		PartitionerImpl partitioner = new PartitionerImpl();
		List<Integer> items = this.generateRandomList(itemsCount);

		partitioner.setDefaultPartitionSize(partitionSize);
		List<List<Integer>> partitions = partitioner.makePartitions(items);

		assertTrue(partitions.size() == 12); // 100 / 9 = (11 + 1/9)

		for (List<Integer> partition : partitions) {
			assertTrue(partition.size() <= partitionSize);
		}

	}

	@Test
	public void testNullCollectionPartitionGenerator() {

		PartitionerImpl partitioner = new PartitionerImpl();
		List<Integer> items = null;

		List<List<Integer>> partitions = partitioner.makePartitionsWithCollection(items);

		assertNotNull(partitions);
		assertTrue(partitions.size() == 0);
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
