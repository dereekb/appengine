package com.dereekb.gae.test.utility.collection;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.collections.list.SetUtility.SetDifferenceImpl;

public class SetUtilityTest {
	
	@Test()
	public void testSetUtilityIntersection() {
		
		Set<Integer> setA = new HashSet<Integer>();

		setA.add(1);
		setA.add(2); // Included in both
		setA.add(3); // Included in both
		
		Set<Integer> setB = new HashSet<Integer>();

		setB.add(2); // Included in both
		setB.add(3); // Included in both
		setB.add(4);
		
		SetDifferenceImpl<Integer> difference = SetUtility.makeSetInfo(setA, setB);
		
		Set<Integer> intersection = difference.getIntersection();
		
		Assert.assertFalse(intersection.contains(1));
		Assert.assertTrue(intersection.contains(2));
		Assert.assertTrue(intersection.contains(3));
		Assert.assertFalse(intersection.contains(4));
	}

	@Test()
	public void testSetUtilityCompliment() {
		
		Set<Integer> setA = new HashSet<Integer>();

		setA.add(1); 
		setA.add(2);
		setA.add(3);
		
		Set<Integer> setB = new HashSet<Integer>();

		setB.add(2);
		setB.add(3);
		setB.add(4); // Should be the only one contained.
		
		SetDifferenceImpl<Integer> difference = SetUtility.makeSetInfo(setA, setB);
		
		Set<Integer> compliment = difference.getCompliment();
		
		Assert.assertFalse(compliment.contains(1));
		Assert.assertFalse(compliment.contains(2));
		Assert.assertFalse(compliment.contains(3));
		Assert.assertTrue(compliment.contains(4));
	}

	@Test()
	public void testSetUtilityDifference() {
		
		Set<Integer> setA = new HashSet<Integer>();

		setA.add(1); // Unique to setA	
		setA.add(2);
		setA.add(3);
		
		Set<Integer> setB = new HashSet<Integer>();

		setB.add(2);
		setB.add(3);
		setB.add(4); // Unique to setB
		
		SetDifferenceImpl<Integer> difference = SetUtility.makeSetInfo(setA, setB);
		
		Set<Integer> setDifference = difference.getDifference();
		
		Assert.assertTrue(setDifference.contains(1));
		Assert.assertFalse(setDifference.contains(2));
		Assert.assertFalse(setDifference.contains(3));
		Assert.assertTrue(setDifference.contains(4));
	}

}
