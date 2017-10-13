package com.dereekb.gae.test.utility.collection.time;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.collections.time.DatedTimeBlock;
import com.dereekb.gae.utilities.collections.time.utility.DatedTimeBlockSetOperations;
import com.dereekb.gae.utilities.collections.time.utility.DatedTimeBlockUtilityInstance;
import com.dereekb.gae.utilities.collections.time.utility.TimeBlockRounding;
import com.dereekb.gae.utilities.collections.time.utility.impl.DatedTimeBlockImpl;
import com.dereekb.gae.utilities.collections.time.utility.impl.TimeBlockReaderImpl;

/**
 * Used for testing dated time utilities.
 * 
 * @author dereekb
 *
 */
public class DatedTimeBlockUtilityTest {

	/**
	 * Tests rounding block values.
	 */
	@Test
	public void testBlockRounding() {

		Long time = 1000L;
		Long timeInPeriod = 500L;

		// Time Period with even floor/ceil values
		Long ceilRound = TimeBlockRounding.CEIL.roundBlockSize(time, timeInPeriod);
		Long floorRound = TimeBlockRounding.FLOOR.roundBlockSize(time, timeInPeriod);

		Assert.assertTrue(ceilRound.equals(2L));
		Assert.assertTrue(floorRound.equals(ceilRound));

		// Time Period with Different floor/ceil values
		timeInPeriod = 300L;

		ceilRound = TimeBlockRounding.CEIL.roundBlockSize(time, timeInPeriod);
		floorRound = TimeBlockRounding.FLOOR.roundBlockSize(time, timeInPeriod);

		Assert.assertTrue(ceilRound.equals(4L));
		Assert.assertTrue(floorRound.equals(3L));

		// Time Period equal to time
		timeInPeriod = time;

		ceilRound = TimeBlockRounding.CEIL.roundBlockSize(time, timeInPeriod);
		floorRound = TimeBlockRounding.FLOOR.roundBlockSize(time, timeInPeriod);

		Assert.assertTrue(ceilRound.equals(1L));
		Assert.assertTrue(floorRound.equals(ceilRound));

		// Time Period with greatly larger timeInPeriod
		timeInPeriod = time * 100000;

		ceilRound = TimeBlockRounding.CEIL.roundBlockSize(time, timeInPeriod);
		floorRound = TimeBlockRounding.FLOOR.roundBlockSize(time, timeInPeriod);

		Assert.assertTrue(ceilRound.equals(1L));
		Assert.assertTrue(floorRound.equals(0L));
	}

	@Test
	public void testTimeBlockReaderImplGetTimeForBlocks() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);

		Long targetBlocks = 5L;
		Long expectedLength = timeInPeriod * targetBlocks;
		
		Long blocks = reader.getTimeForBlocks(targetBlocks);
		
		Assert.assertTrue(expectedLength.equals(blocks));
	}

	@Test
	public void testTimeBlockReaderImplGetBlocksForDates() {
		Long timeInPeriod = 500L;
		
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);

		Long targetBlocks = 5L;
		Date start = new Date(0);
		Date end = new Date(timeInPeriod * targetBlocks);

		Long ceilBlocks = reader.getBlocksForDates(start, end, TimeBlockRounding.CEIL);
		Long floorBlocks = reader.getBlocksForDates(start, end, TimeBlockRounding.FLOOR);

		Assert.assertTrue(ceilBlocks.equals(targetBlocks));
		Assert.assertTrue(floorBlocks.equals(targetBlocks));
	}

	@Test
	public void testTimeBlockReaderImplGetDateEnd() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);

		Long targetBlocks = 5L;
		Date start = new Date(0);
		Date expectedEnd = new Date(timeInPeriod * targetBlocks);
		
		Date end = reader.getDateEnd(start, targetBlocks);
		
		Assert.assertTrue(expectedEnd.equals(end));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceGetDateEnd() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);

		Long targetBlocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(targetBlocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);
		Date endDate = instance.getEndDate();

		Date expectedEnd = new Date(timeInPeriod * targetBlocks);
		
		Assert.assertTrue(expectedEnd.equals(endDate));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceGetEndDateInlineWithDate() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);

		Long targetBlocks = 5L;
		Date startDate = new Date(0);
		Date endDate = new Date((timeInPeriod * targetBlocks) + (timeInPeriod / 2));

		Date ceilDate = reader.makeEndDateInlineWithDate(endDate, startDate, TimeBlockRounding.CEIL);
		Date floorDate = reader.makeEndDateInlineWithDate(endDate, startDate, TimeBlockRounding.FLOOR);
		
		Long ceilBlocks = reader.getBlocksForDates(startDate, ceilDate, TimeBlockRounding.CEIL);
		Long floorBlocks = reader.getBlocksForDates(startDate, floorDate, TimeBlockRounding.CEIL);

		Assert.assertTrue(ceilBlocks.equals(targetBlocks + 1L));
		Assert.assertTrue(floorBlocks.equals(targetBlocks));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorGetFirstOverlapIndexWithNoOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Long secondBlocks = 3L;
		Date secondStart = new Date(timeInPeriod * secondBlocks);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		Long expectedConnectionIndex = 2L;
		Assert.assertTrue(operations.getFirstConnectionIndex().equals(expectedConnectionIndex));
	}

	public void testTimeBlockReaderImplUtilityInstanceOperatorHasOverlapWithOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date secondStart = new Date(timeInPeriod * 3L);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		Assert.assertTrue(operations.hasOverlap());
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorIsContinuousBackToBack() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date secondStart = new Date(timeInPeriod * blocks);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		Assert.assertTrue(operations.isContinuous());
	}
	
	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorHasOverlapBackToBack() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date secondStart = new Date(timeInPeriod * blocks);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		Assert.assertFalse(operations.hasOverlap());
	}
	
	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorHasOverlapWithNoOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date secondStart = new Date(timeInPeriod * 6L);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		Assert.assertFalse(operations.hasOverlap());
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorFirstOverlapIndexWithOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date secondStart = new Date(timeInPeriod * blocks);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		Long firstOverlapIndex = operations.getFirstConnectionIndex();
		Assert.assertTrue(firstOverlapIndex == (blocks - 1));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorGetUnionWithUnevenOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Long overlap = 1L;
		Date secondStart = new Date(timeInPeriod * (blocks - overlap) + (timeInPeriod / 2));
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);

		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock union = operations.getUnion();
		Assert.assertTrue(union.getTimeBlocks() == (blocks * 2) - 1);	// -1 for overlap round down.
		Assert.assertTrue(union.getTimeBlockStart().equals(start));
		
		Date unionDateEnd = reader.getDateEnd(union);
		Date secondDateEnd = reader.getDateEnd(startDatedTimeBlock);
		Integer comparison = unionDateEnd.compareTo(secondDateEnd);
		Assert.assertTrue(comparison <= 0);
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorGetUnion() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date secondStart = new Date(timeInPeriod * blocks);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock union = operations.getUnion();
		
		Date unionDateEnd = reader.getDateEnd(union);
		Date secondDateEnd = reader.getDateEnd(startDatedTimeBlock);
		Integer comparison = unionDateEnd.compareTo(secondDateEnd);
		Assert.assertTrue(comparison <= 0);
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorGetUniqueWithNoOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date secondStart = new Date(timeInPeriod * blocks);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock unique = operations.getUnique();
		Assert.assertTrue(datedTimeBlock.getTimeBlocks() == unique.getTimeBlocks());
		Assert.assertTrue(datedTimeBlock.getTimeBlockStart().equals(unique.getTimeBlockStart()));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorGetUniqueWithInlineOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Long overlap = 1L;
		Date secondStart = new Date(timeInPeriod * (blocks - overlap));
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock unique = operations.getUnique();

		Long expectedUniqueLength = datedTimeBlock.getTimeBlocks() - overlap;
		Assert.assertTrue(unique.getTimeBlocks().equals(expectedUniqueLength));
		Assert.assertTrue(datedTimeBlock.getTimeBlockStart().equals(unique.getTimeBlockStart()));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorGetUniqueWithNonInlineOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Long overlap = 1L;
		Date secondStart = new Date(timeInPeriod * (blocks - overlap) + (timeInPeriod / 2));
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);

		Assert.assertFalse(reader.datesAreInline(datedTimeBlock, startDatedTimeBlock));
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock unique = operations.getUnique();
		Assert.assertTrue((datedTimeBlock.getTimeBlocks() - overlap) == unique.getTimeBlocks());
		Assert.assertTrue(datedTimeBlock.getTimeBlockStart().equals(unique.getTimeBlockStart()));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorGetIntersectionWithOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Long overlap = 1L;
		Date secondStart = new Date(timeInPeriod * (blocks - overlap) + (timeInPeriod / 2));
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);

		Assert.assertFalse(reader.datesAreInline(datedTimeBlock, startDatedTimeBlock));
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock intersection = operations.getIntersection();

		Date startEnd = reader.getDateEnd(datedTimeBlock);
		Date intersectionEnd = reader.getDateEnd(intersection);
		
		Assert.assertTrue(intersection.getTimeBlocks() == overlap);
		Assert.assertTrue(startEnd.equals(intersectionEnd));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorGetIntersectionWithNoOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date secondStart = new Date(timeInPeriod * blocks);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock intersection = operations.getIntersection();

		Date startEnd = reader.getDateEnd(datedTimeBlock);
		Date intersectionEnd = reader.getDateEnd(intersection);
		
		Assert.assertTrue(intersection.getTimeBlocks() == 0);
		Assert.assertTrue(startEnd.equals(intersectionEnd));
	}
	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorGetIntersectionWithInlineOverlapAndBiggerStart() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		Long realBlocks = blocks * 3;
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(realBlocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date secondStart = new Date(timeInPeriod * blocks);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock intersection = operations.getIntersection();

		Date secondStartEnd = reader.getDateEnd(startDatedTimeBlock);
		Date intersectionEnd = reader.getDateEnd(intersection);
		
		Long expectedIntersectionSize = startDatedTimeBlock.getTimeBlocks();
		Assert.assertTrue(intersection.getTimeBlocks() == expectedIntersectionSize);
		Assert.assertTrue(secondStartEnd.equals(intersectionEnd));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorGetCompliment() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(blocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date secondStart = new Date(timeInPeriod * blocks);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock compliment = operations.getCompliment();

		Date secondStartEnd = reader.getDateEnd(startDatedTimeBlock);
		Date complimentEnd = reader.getDateEnd(compliment);
		
		Assert.assertTrue(compliment.getTimeBlocks() == blocks);
		Assert.assertTrue(secondStartEnd.equals(complimentEnd));
	}
	
	// MARK: Splitting
	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorSplitGetUniqueWithUnevenOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		Long allBlocks = blocks * 3;
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(allBlocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Long overlap = 1L;
		Date secondStart = new Date(timeInPeriod * (blocks - overlap) + (timeInPeriod / 2));
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		Assert.assertFalse(reader.datesAreInline(datedTimeBlock, startDatedTimeBlock));
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock unique = operations.getUnique();
		Assert.assertTrue((blocks - overlap) == unique.getTimeBlocks());
		Assert.assertTrue(datedTimeBlock.getTimeBlockStart().equals(unique.getTimeBlockStart()));
		Assert.assertTrue(unique.getTimeBlockStart().before(startDatedTimeBlock.getTimeBlockStart()));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorSplitGetUnion() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		Long allBlocks = blocks * 3;
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(allBlocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date secondStart = new Date(timeInPeriod * blocks);
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock union = operations.getUnion();
		Assert.assertTrue(union.getTimeBlocks() == blocks * 2);
		Assert.assertTrue(union.getTimeBlockStart().equals(start));
		Assert.assertTrue(reader.getDateEnd(union).equals(reader.getDateEnd(startDatedTimeBlock)));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorSplitGetUnionWithUnevenOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 5L;
		Date start = new Date(0);
		Long allBlocks = blocks * 3;
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(allBlocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Long overlap = 1L;
		Date secondStart = new Date(timeInPeriod * (blocks - overlap) + (timeInPeriod / 2));
		DatedTimeBlockImpl startDatedTimeBlock = new DatedTimeBlockImpl(blocks, secondStart);
		
		DatedTimeBlockSetOperations operations = instance.operatorWith(startDatedTimeBlock);
		DatedTimeBlock union = operations.getUnion();

		Assert.assertTrue(union.getTimeBlocks() == (blocks * 2) - 1);
		Assert.assertTrue(union.getTimeBlockStart().equals(start));
		
		Date unionDateEnd = reader.getDateEnd(union);
		Date secondDateEnd = reader.getDateEnd(startDatedTimeBlock);
		Integer comparison = unionDateEnd.compareTo(secondDateEnd);
		Assert.assertTrue(comparison <= 0);
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorSplitWithNotInlineOverlapAndNoExpectedEnd() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 2L;
		Date start = new Date(0);
		Long allBlocks = blocks * 2 + 1;	// 5 Blocks Long
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(allBlocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date splitStart = new Date((timeInPeriod * blocks) + (timeInPeriod / 2)); //Starts at 2.75 blocks
		DatedTimeBlockImpl splitTimeBlock = new DatedTimeBlockImpl(blocks, splitStart);	//2 blocks long
		
		List<DatedTimeBlock> split = instance.split(splitTimeBlock);

		Assert.assertTrue(split.size() == 2);
		
		Long totalBlocks = split.get(0).getTimeBlocks() + split.get(1).getTimeBlocks();
		Long expectedTotalBlocks = allBlocks - (split.size() - 1L);	// Since offset, will be missing blocks.
		
		Assert.assertTrue(totalBlocks.equals(expectedTotalBlocks));
		Assert.assertTrue(split.get(0).getTimeBlocks().equals(blocks));
		Assert.assertTrue(split.get(1).getTimeBlocks().equals(splitTimeBlock.getTimeBlocks()));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorSplitWithNotInlineOverlap() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 2L;
		Date start = new Date(0);
		Long allBlocks = blocks * 3;	// 6 Blocks Long
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(allBlocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);
		
		// Split at the 3rd block.
		Date splitStart = new Date((timeInPeriod * blocks) + (timeInPeriod / 2)); //Starts at 2.75 blocks
		DatedTimeBlockImpl splitTimeBlock = new DatedTimeBlockImpl(blocks, splitStart);	//2 blocks long
		
		List<DatedTimeBlock> split = instance.split(splitTimeBlock);

		Assert.assertTrue(split.size() == 3);
		
		Long totalBlocks = split.get(0).getTimeBlocks() + split.get(1).getTimeBlocks();
		Long expectedTotalBlocks = allBlocks - (split.size() - 1L);
		
		Assert.assertTrue(totalBlocks.equals(expectedTotalBlocks));
		Assert.assertTrue(split.get(0).getTimeBlocks().equals(blocks));
		Assert.assertTrue(split.get(1).getTimeBlocks().equals(splitTimeBlock.getTimeBlocks()));
		Assert.assertTrue(split.get(2).getTimeBlocks().equals(1L));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorSplitWithExact() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 2L;
		Date start = new Date(0);
		Long allBlocks = blocks * 2 + 1;	// 5 Blocks Long
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(allBlocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);
		List<DatedTimeBlock> split = instance.split(datedTimeBlock);
		
		Assert.assertTrue(split.size() == 1);
		Assert.assertTrue(split.get(0).getTimeBlocks().equals(datedTimeBlock.getTimeBlocks()));
		Assert.assertTrue(split.get(0).getTimeBlockStart().equals(datedTimeBlock.getTimeBlockStart()));
	}

	@Test
	public void testTimeBlockReaderImplUtilityInstanceOperatorSplitInlineWithNoExpectedHead() {
		Long timeInPeriod = 500L;
		TimeBlockReaderImpl reader = new TimeBlockReaderImpl(timeInPeriod);
		
		Long blocks = 2L;
		Date start = new Date(0);
		Long allBlocks = blocks * 2 + 1;	// 5 Blocks Long
		DatedTimeBlockImpl datedTimeBlock = new DatedTimeBlockImpl(allBlocks, start);
		
		DatedTimeBlockUtilityInstance instance = reader.makeInstance(datedTimeBlock);

		Date splitStart = start;
		DatedTimeBlockImpl splitTimeBlock = new DatedTimeBlockImpl(blocks, splitStart);	//2 blocks long
		
		List<DatedTimeBlock> split = instance.split(splitTimeBlock);

		Assert.assertTrue(split.size() == 2);
		
		Long totalBlocks = split.get(0).getTimeBlocks() + split.get(1).getTimeBlocks();
		Long expectedTotalBlocks = allBlocks;	// Since offset, will be missing blocks.
		
		Assert.assertTrue(totalBlocks.equals(expectedTotalBlocks));
		Assert.assertTrue(split.get(0).getTimeBlocks().equals(blocks));
		Assert.assertTrue(split.get(1).getTimeBlocks().equals(allBlocks - splitTimeBlock.getTimeBlocks()));
	}

}
